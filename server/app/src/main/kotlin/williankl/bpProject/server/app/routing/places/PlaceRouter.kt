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
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.data.placeService.models.SavingPlace
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.generateId
import williankl.bpProject.server.app.parseOrNull
import williankl.bpProject.server.app.routing.places.PlaceMapper.toPlace
import williankl.bpProject.server.app.runOrNull
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.PlaceStorage
import java.util.UUID

internal object PlaceRouter {

    private val placesService by serverDi.instance<PlaceStorage>()

    fun Route.placesRoute() {
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
            val received = runOrNull<SavingPlace> { call.receive() }
            if (received != null) {
                val generatedPlace = received.toPlace(generateId) // fixme - use user id once created
                placesService.savePlace(generatedPlace)
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
                val placesFound = placesService.retrievePlaces(page, limit)
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
            val id = call.parameters["id"]
            val uuid = parseOrNull { UUID.fromString(id) }
            if (uuid != null) {
                val placeFound = placesService.retrievePlace(uuid)
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
