package williankl.bpProject.server.app.routing.places

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.idFromParameter
import williankl.bpProject.server.app.routing.BPRoute
import williankl.bpProject.server.app.routing.places.PlaceMapper.retrieveDistanceQuery
import williankl.bpProject.server.app.routing.places.PlaceMapper.retrieveStateQuery
import williankl.bpProject.server.app.routing.places.PlaceMapper.toPlace
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.app.userId
import williankl.bpProject.server.database.services.PlaceStorage
import williankl.bpProject.server.database.services.UserStorage

internal class PlaceRouter(
    private val placeStorage: PlaceStorage,
    private val userStorage: UserStorage,
): BPRoute {

    context (Route)
    override fun route() {
        route("/places") {
            pagingRouting()
            idRouting()
            defaultRoute()

            authenticate(AuthenticationHandler.BEARER_KEY) {
                savePlaceRoute()
            }
        }
    }

    private fun Route.savePlaceRoute() {
        post {
            val received = runOrNullSuspend { call.receive<SavingPlaceRequest>() }
            val userId = call.userId

            if (received != null && userId != null) {
                val generatedPlace = received.toPlace(userId)
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
