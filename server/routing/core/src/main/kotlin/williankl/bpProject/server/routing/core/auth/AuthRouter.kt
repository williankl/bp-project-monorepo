package williankl.bpProject.server.routing.core.auth

import com.benasher44.uuid.uuid4
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.cypher.BeautifulCypher
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.ServerConstants.BEARER_KEY
import williankl.bpProject.server.core.bearer
import williankl.bpProject.server.database.services.AuthStorage

internal class AuthRouter(
    private val cypher: BeautifulCypher,
    private val authStorage: AuthStorage,
) : BPRoute {

    context (Route)
    override fun route() {
        route("/auth") {
            formLoginRoute()
            signupRoute()

            authenticate(BEARER_KEY) {
                loggingOutRoute()
            }
        }
    }

    private fun Route.formLoginRoute() {
        get("/login") {
            val credential = call.parameters["credential"]
            val password = call.parameters["password"]

            val foundUser = credential?.let {
                this@AuthRouter.authStorage.retrieveUser(credential)
            }

            if (foundUser != null && password != null) {
                val userPassword = this@AuthRouter.authStorage.userEncryptedPassword(foundUser.id)
                    ?.let(cypher::decrypt)

                if (userPassword == password) {
                    val generatedBearerToken = generateBearerToken()

                    this@AuthRouter.authStorage.attachBearerToken(
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

                this@AuthRouter.authStorage.createUser(
                    user = createdUser,
                    encryptedPassword = cypher.encrypt(password),
                )

                this@AuthRouter.authStorage.attachBearerToken(
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
            val bearerToken = bearer
            if (bearerToken != null) {
                this@AuthRouter.authStorage.invalidateBearerToken(bearerToken)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotAcceptable)
            }
        }
    }

    private fun generateBearerToken(): String {
        return uuid4().toString()
    }
}
