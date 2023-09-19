package williankl.bpProject.server.app.routing.user

import com.benasher44.uuid.uuidFrom
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.UserStorage

internal object UserRouter {

    private val userService by serverDi.instance<UserStorage>()

    fun Route.userRoutes() {
        route("/user") {
            authenticate(AuthenticationHandler.BEARER_KEY) {
                currentUserRoute()
                logUserOutRoute()
            }
        }
    }

    private fun Route.currentUserRoute() {
        get {
            val userId = call.principal<UserIdPrincipal>()
                ?.name
                ?.let(::uuidFrom)

            val foundUser = userId?.let {
                userService.retrieveUser(userId)
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

    private fun Route.logUserOutRoute() {
        delete {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = NetworkErrorResponse(
                    message = "Bearer token invalidation not implemented"
                )
            )
        }
    }
}
