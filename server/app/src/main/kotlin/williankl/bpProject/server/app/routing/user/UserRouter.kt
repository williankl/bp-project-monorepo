package williankl.bpProject.server.app.routing.user

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.kodein.di.instance
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.response.NetworkErrorResponse
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.server.app.configuration.AuthenticationHandler
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.UserStorage

internal object UserRouter {

    private val userService by serverDi.instance<UserStorage>()

    fun Route.userRoutes() {
        route("/user") {
            formLoginRoute()
            signupRoute()
            defaultUser()
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
                    ?.let(::decryptPassword)

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
        post("/signup") {
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
                    encryptedPassword = encryptPassword(password),
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

    private fun Route.defaultUser() {
        authenticate(AuthenticationHandler.BEARER_KEY) {
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
    }

    private fun generateBearerToken(): String {
        return uuid4().toString()
    }

    private fun encryptPassword(password: String): String {
        return password
    }

    private fun decryptPassword(encryptedPassword: String): String {
        return encryptedPassword
    }
}