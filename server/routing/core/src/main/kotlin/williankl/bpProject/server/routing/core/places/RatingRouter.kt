package williankl.bpProject.server.routing.core.places

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.common.data.placeService.models.PlaceRatingData
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY
import williankl.bpProject.server.core.idFromParameter
import williankl.bpProject.server.core.userId
import williankl.bpProject.server.database.services.PlaceRatingStorage

internal class RatingRouter(
    private val placesRatingStorage: PlaceRatingStorage
) : BPRoute {

    context (Route)
    override fun route() {
        route("/places/rating") {
            metadataRoute()
            listRatings()
            authenticate(BEARER_KEY) {
                rateRoute()
                deleteRatingRoute()
            }
        }
    }

    private fun Route.metadataRoute() {
        get("/metadata") {
            val placeId = idFromParameter("placeId")

            if (placeId != null) {
                val ratings = placesRatingStorage.ratingsForPlace(
                    placeId = placeId,
                    page = 0,
                    limit = Int.MAX_VALUE,
                )

                val result = if (ratings.isNotEmpty()) {
                    PlaceRatingData(
                        ratingCount = ratings.size,
                        rating = ratings.sumOf { rating -> rating.rating } / ratings.size.toFloat(),
                    )
                } else {
                    PlaceRatingData(
                        ratingCount = 0,
                        rating = 0f,
                    )
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = result,
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "Could not find 'placeId' parameter"
                    )
                )
            }
        }
    }

    private fun Route.rateRoute() {
        post {
            val received = runOrNullSuspend { call.receive<PlaceRatingRequest>() }
            val userId = call.userId
            val placeId = idFromParameter("placeId")

            if (placeId != null && received != null && userId != null) {
                val result = placesRatingStorage.createRating(
                    placeId = placeId,
                    ownerId = userId,
                    data = received,
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = result,
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "Received an unexpected 'PlaceRatingRequest' body"
                    )
                )
            }
        }
    }

    private fun Route.listRatings() {
        get {
            val page = call.parameters["page"]?.toIntOrNull()
            val limit = call.parameters["limit"]?.toIntOrNull()
            val placeId = idFromParameter("placeId")

            if (placeId != null && page != null && limit != null) {
                val result = placesRatingStorage.ratingsForPlace(placeId, page, limit)
                when {
                    result.isEmpty() -> call.respond(
                        status = HttpStatusCode.NoContent,
                        message = result,
                    )

                    else -> call.respond(
                        status = HttpStatusCode.OK,
                        message = result,
                    )
                }
            }
        }
    }

    private fun Route.deleteRatingRoute() {
        delete {
            val userId = call.userId
            val ratingId = idFromParameter("ratingId")
            val targetRating = ratingId?.let {
                placesRatingStorage.retrieveRating(ratingId)
            }

            if (targetRating != null && userId != null) {
                if (targetRating.ownerData.id == userId) {
                    placesRatingStorage.deleteRating(ratingId)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = NetworkErrorResponse(
                            code = "not-owned",
                            message = "User is not allowed to modify such item"
                        )
                    )
                }
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = NetworkErrorResponse(
                        message = "Missing user id or rating id"
                    )
                )
            }
        }
    }
}
