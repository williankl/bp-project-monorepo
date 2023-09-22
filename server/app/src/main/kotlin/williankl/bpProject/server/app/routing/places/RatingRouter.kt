package williankl.bpProject.server.app.routing.places

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
import org.kodein.di.instance
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.idFromParameter
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.app.userId
import williankl.bpProject.server.database.services.PlaceRatingStorage

internal object RatingRouter {

    private val placesRatingService by serverDi.instance<PlaceRatingStorage>()

    fun Route.ratingRoute() {
        route("/places/rating") {
            listRatings()
            authenticate(AuthenticationHandler.BEARER_KEY) {
                rateRoute()
                deleteRatingRoute()
            }
        }
    }

    private fun Route.rateRoute() {
        post {
            val received = runOrNullSuspend { call.receive<PlaceRatingRequest>() }
            val userId = call.userId
            val placeId = idFromParameter("placeId")

            if (placeId != null && received != null && userId != null) {
                placesRatingService.createRating(
                    placeId = placeId,
                    ownerId = userId,
                    data = received,
                )

                call.respond(HttpStatusCode.OK)
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
                val result = placesRatingService.ratingsForPlace(placeId, page, limit)
                when {
                    result.isEmpty() -> call.respond(HttpStatusCode.NoContent)
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
                placesRatingService.retrieveRating(ratingId)
            }

            if (targetRating != null && userId != null) {
                if (targetRating.ownerData.id == userId) {
                    placesRatingService.deleteRating(ratingId)
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
