package williankl.bpProject.server.app.routing.maps

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.runOrNull
import williankl.bpProject.common.data.placeService.MapsService
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.routing.BPRoute

internal class MapsRouter(
    private val mapsService: MapsService
) : BPRoute {
    context(Route)
    override fun route() {
        route("/maps") {
            authenticate(AuthenticationHandler.BEARER_KEY) {
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
}