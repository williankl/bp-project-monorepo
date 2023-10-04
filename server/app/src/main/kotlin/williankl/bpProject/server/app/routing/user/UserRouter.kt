package williankl.bpProject.server.app.routing.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.routing.BPRoute
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.app.userId
import williankl.bpProject.server.database.services.UserStorage

internal class UserRouter(
    private val userStorage: UserStorage,
) : BPRoute {

    context (Route)
    override fun route() {
        route("/user") {
            authenticate(AuthenticationHandler.BEARER_KEY) {
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
