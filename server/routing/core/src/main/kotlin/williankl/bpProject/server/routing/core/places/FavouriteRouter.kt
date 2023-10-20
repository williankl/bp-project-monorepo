package williankl.bpProject.server.routing.core.places

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY
import williankl.bpProject.server.core.idFromParameter
import williankl.bpProject.server.core.userId
import williankl.bpProject.server.database.services.FavouriteStorage

internal class FavouriteRouter(
    private val favouriteStorage: FavouriteStorage
) : BPRoute {

    context (Route)
    override fun route() {
        route("/places/favourite") {
            authenticate(BEARER_KEY) {
                isFavouriteRoute()
                favouritePlacesRoute()
                favouritePlacesSetting()
            }
        }
    }

    private fun Route.isFavouriteRoute() {
        get {
            val placeId = idFromParameter("placeId")
            val userId = call.userId

            if (placeId != null && userId != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = favouriteStorage.isFavourite(userId, placeId)
                )
            }
        }
    }

    private fun Route.favouritePlacesRoute() {
        get {
            val userId = call.userId
            val page = call.parameters["page"]?.toIntOrNull()
            val limit = call.parameters["limit"]?.toIntOrNull()

            if (page != null && limit != null && userId != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = favouriteStorage.userFavourites(userId, page, limit)
                )
            }
        }
    }

    private fun Route.favouritePlacesSetting() {
        post {
            val userId = call.userId
            val placeId = idFromParameter("placeId")
            val settingTo = call.parameters["settingTo"]?.toBooleanStrictOrNull()

            if (placeId != null && userId != null && settingTo != null) {
                favouriteStorage.setFavouriteTo(userId, placeId, settingTo)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
