package williankl.bpProject.server.routing.core.maps

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.runOrNull
import williankl.bpProject.common.core.runOrNullSuspend
import williankl.bpProject.common.data.placeService.models.DistanceRequest
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY

internal class MapsRouter(
    private val mapsService: MapsService
) : BPRoute {
    context(Route)
    override fun route() {
        route("/maps") {
            distanceRouting()
            authenticate(BEARER_KEY) {
                queryRouting()
                coordinatesRouting()
            }
        }
    }

    private fun Route.queryRouting() {
        get("/search") {
            val queryString = call.parameters["query"]
            if (queryString != null && queryString.length > 3) {
                call.respond(
                    message = mapsService.queryForPlace(queryString),
                    status = HttpStatusCode.OK,
                )
            } else {
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }

    private fun Route.coordinatesRouting() {
        get {
            val latitude = runOrNull { call.parameters["latitude"]?.toDoubleOrNull() }
            val longitude = runOrNull { call.parameters["longitude"]?.toDoubleOrNull() }

            if (latitude != null && longitude != null) {
                call.respond(
                    message = mapsService.placeFromCoordinates(
                        coordinates = MapCoordinate(latitude, longitude)
                    ),
                    status = HttpStatusCode.OK,
                )
            }
        }
    }

    private fun Route.distanceRouting() {
        post("/distance") {
            val distanceRequest = runOrNullSuspend { call.receive<DistanceRequest>() }

            if (distanceRequest != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = mapsService.distanceBetween(
                        from = distanceRequest.userCoordinate,
                        to = distanceRequest.destinationCoordinates.toTypedArray(),
                    )
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "The distance request was not found or was incorrectly built",
                )
            }
        }
    }
}
