package williankl.bpProject.server.app.routing.auth

import com.benasher44.uuid.uuid4
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.cypher.BeautifulCypher
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.AuthStorage

internal object AuthRouter {

    private val cypher by serverDi.instance<BeautifulCypher>()
    private val authService by serverDi.instance<AuthStorage>()

    fun Route.authRoutes() {
        route("/auth") {
            formLoginRoute()
            signupRoute()

            authenticate(AuthenticationHandler.BEARER_KEY) {
                loggingOutRoute()
            }
        }
    }

    private fun Route.formLoginRoute() {
        get("/login") {
            val credential = call.parameters["credential"]
            val password = call.parameters["password"]

            val foundUser = credential?.let {
                authService.retrieveUser(credential)
            }

            if (foundUser != null && password != null) {
                val userPassword = authService.userEncryptedPassword(foundUser.id)
                    ?.let(cypher::decrypt)

                if (userPassword == password) {
                    val generatedBearerToken = generateBearerToken()

                    authService.attachBearerToken(
                        userId = foundUser.id,
                        token = generatedBearerToken,
                    )

                    call.respond(
                        status = HttpStatusCode.OK,
                        message = UserCredentialResponse(
                            bearerToken = generatedBearerToken,
                            user = foundUser,
                        ),
                    )
                }
            }

            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = NetworkErrorResponse(
                    message = "Email or password are invalid"
                )
            )
        }
    }

    private fun Route.signupRoute() {
        get("/signup") {
            val email = call.parameters["email"]
            val name = call.parameters["name"]
            val password = call.parameters["password"]

            if (email != null && name != null && password != null) {
                val createdUser = User(
                    id = uuid4(),
                    email = email,
                    name = name,
                    avatarUrl = null,
                    tag = null,
                )

                val generatedBearerToken = generateBearerToken()

                authService.createUser(
                    user = createdUser,
                    encryptedPassword = cypher.encrypt(password),
                )

                authService.attachBearerToken(
                    userId = createdUser.id,
                    token = generatedBearerToken,
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = UserCredentialResponse(
                        bearerToken = generatedBearerToken,
                        user = createdUser,
                    ),
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NetworkErrorResponse(
                        message = "Email | Tag | Password are in invalid format"
                    )
                )
            }
        }
    }

    private fun Route.loggingOutRoute() {
        delete {
            val bearerToken = call.parameters["Authorization"]
            if (bearerToken != null) {
                authService.invalidateBearerToken(bearerToken)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }

    private fun generateBearerToken(): String {
        return uuid4().toString()
    }
}
