package williankl.bpProject.server.routing.core.places

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.server.core.userId
import williankl.bpProject.server.core.idFromParameter
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY
import williankl.bpProject.server.database.services.PlaceStorage
import williankl.bpProject.server.database.services.UserStorage
import williankl.bpProject.server.routing.core.places.PlaceMapper.retrieveDistanceQuery
import williankl.bpProject.server.routing.core.places.PlaceMapper.retrieveStateQuery
import williankl.bpProject.server.routing.core.places.PlaceMapper.toPlace

internal class PlaceRouter(
    private val placeStorage: PlaceStorage,
    private val userStorage: UserStorage,
) : BPRoute {

    context (Route)
    override fun route() {
        route("/places") {
            pagingRouting()
            idRouting()
            defaultRoute()

            authenticate(BEARER_KEY) {
                savePlaceRoute()
            }
        }
    }

    private fun Route.savePlaceRoute() {
        post {
            val received = runOrNullSuspend { call.receive<SavingPlaceRequest>() }
            val user = call.userId?.let { id -> userStorage.retrieveUser(id) }

            if (received != null && user != null) {
                val generatedPlace = received.toPlace(user)
                placeStorage.savePlace(generatedPlace)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "Received an unexpected 'Place' body"
                    )
                )
            }
        }
    }

    private fun Route.pagingRouting() {
        get {
            val page = call.parameters["page"]?.toIntOrNull()
            val limit = call.parameters["limit"]?.toIntOrNull()

            if (page != null && limit != null) {
                val ownerId = idFromParameter("ownerId")
                val distanceQuery = retrieveDistanceQuery()
                val state = retrieveStateQuery()

                val placesFound = placeStorage.retrievePlaces(page, limit, ownerId, state, distanceQuery)
                when {
                    placesFound.isEmpty() -> call.respond(HttpStatusCode.NoContent)
                    else -> call.respond(
                        status = HttpStatusCode.OK,
                        message = placesFound
                    )
                }
            }
        }
    }

    private fun Route.idRouting() {
        get {
            val uuid = idFromParameter("placeId")
            if (uuid != null) {
                val placeFound = placeStorage.retrievePlace(uuid)
                when {
                    placeFound == null -> call.respond(
                        status = HttpStatusCode.NoContent,
                        message = NetworkErrorResponse(
                            message = "No content found"
                        )
                    )

                    else -> call.respond(
                        status = HttpStatusCode.OK,
                        message = placeFound
                    )
                }
            }
        }
    }

    private fun Route.defaultRoute() {
        get {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = NetworkErrorResponse(
                    message = "No 'limit' and 'page' or 'id' found on path parameters"
                )
            )
        }
    }
}
