package williankl.bpProject.server.app.routing.auth

import com.benasher44.uuid.uuid4
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.cypher.BeautifulCypher
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.UserStorage

internal object AuthRouter {

    private val cypher by serverDi.instance<BeautifulCypher>()
    private val userService by serverDi.instance<UserStorage>()

    fun Route.authRoutes() {
        route("/auth") {
            formLoginRoute()
            signupRoute()
        }
    }

    private fun Route.formLoginRoute() {
        get("/login") {
            val credential = call.parameters["credential"]
            val password = call.parameters["password"]

            val foundUser = credential?.let {
                userService.retrieveUser(credential)
            }

            if (foundUser != null && password != null) {
                val userPassword = userService.userEncryptedPassword(foundUser.id)
                    ?.let(cypher::decrypt)

                if (userPassword == password) {
                    val generatedBearerToken = generateBearerToken()

                    userService.attachBearerToken(
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
            val tag = call.parameters["tag"]
            val password = call.parameters["password"]

            if (email != null && tag != null && password != null) {
                val createdUser = User(
                    id = uuid4(),
                    email = email,
                    tag = tag,
                )

                val generatedBearerToken = generateBearerToken()

                userService.attachBearerToken(
                    userId = createdUser.id,
                    token = generatedBearerToken,
                )

                userService.createUser(
                    user = createdUser,
                    encryptedPassword = cypher.encrypt(password),
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

    private fun generateBearerToken(): String {
        return uuid4().toString()
    }
}
