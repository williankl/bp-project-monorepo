package williankl.bpProject.server.app.routing.places

import com.benasher44.uuid.uuidFrom
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.common.data.placeService.models.SavingPlace
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.routing.places.PlaceMapper.retrieveDistanceQuery
import williankl.bpProject.server.app.routing.places.PlaceMapper.retrieveQueryOwnerId
import williankl.bpProject.server.app.routing.places.PlaceMapper.retrieveStateQuery
import williankl.bpProject.server.app.routing.places.PlaceMapper.toPlace
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
            val received = runOrNullSuspend { call.receive<SavingPlace>() }
            val userId = call.principal<UserIdPrincipal>()
                ?.name
                ?.let(::uuidFrom)

            if (received != null && userId != null) {
                val generatedPlace = received.toPlace(userId)
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
                val ownerId = retrieveQueryOwnerId()
                val distanceQuery = retrieveDistanceQuery()
                val state = retrieveStateQuery()

                val placesFound = placesService.retrievePlaces(page, limit, ownerId, state, distanceQuery)
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
            val uuid = runOrNullSuspend { UUID.fromString(id) }
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
