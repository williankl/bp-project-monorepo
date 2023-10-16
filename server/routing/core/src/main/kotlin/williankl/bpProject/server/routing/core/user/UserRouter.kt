package williankl.bpProject.server.routing.core.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY
import williankl.bpProject.server.core.userId
import williankl.bpProject.server.database.services.UserStorage

internal class UserRouter(
    private val userStorage: UserStorage,
) : BPRoute {

    context (Route)
    override fun route() {
        route("/user") {
            authenticate(BEARER_KEY) {
                currentUserRoute()
            }
        }
    }

    private fun Route.currentUserRoute() {
        get {
            val userId = call.userId

            val foundUser = userId?.let {
                userStorage.retrieveUser(userId)
            }

            when {
                foundUser != null -> {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = foundUser,
                    )
                }

                else -> {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = NetworkErrorResponse(
                            message = "Bearer token holder not found"
                        )
                    )
                }
            }
        }
    }
}
