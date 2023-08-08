package williankl.bpProject.server.app.routing.places

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.util.UUID
import org.kodein.di.instance
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.network.NetworkErrorResponse
import williankl.bpProject.server.app.parseOrNull
import williankl.bpProject.server.app.parseOrNullSuspend
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.PlaceStorage

internal object PlaceRouter {

    private val placesService by serverDi.instance<PlaceStorage>()

    fun Route.placesRoute() {
        route("/places") {
            savePlaceRoute()
            pagingRouting()
            idRouting()
        }
    }

    private fun Route.savePlaceRoute() {
        post {
            val received = parseOrNullSuspend { call.receive<Place>() }
            if (received != null) {
                placesService.savePlace(received)
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
        get("{page?}{limit?}") {
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
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "No 'limit' or 'page' found on path parameters"
                    )
                )
            }
        }
    }

    private fun Route.idRouting() {
        get("{id?}") {
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
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "No parameter 'id' found"
                    )
                )
            }
        }
    }
}