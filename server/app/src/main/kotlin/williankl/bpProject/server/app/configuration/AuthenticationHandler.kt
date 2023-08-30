package williankl.bpProject.server.app.configuration

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.auth.bearer
import org.kodein.di.instance
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.UserStorage

internal object AuthenticationHandler {

    private val userStorage by serverDi.instance<UserStorage>()

    fun AuthenticationConfig.basicConfig() {
        basic {
            validate { credentials ->
                userStorage.retrieveUser(credentials.name)
                    ?.let { user ->
                        val isPasswordCorrect = credentials.password == userStorage.userEncryptedPassword(user.id)
                        if (isPasswordCorrect) UserIdPrincipal(user.id.toString())
                        else null
                    }
            }
        }
    }

    fun AuthenticationConfig.bearerConfig() {
        bearer {
            authenticate { tokenCredential ->
                userStorage.findUserByBearer(tokenCredential.token)
                    ?.let { user ->
                        UserIdPrincipal(user.id.toString())
                    }
            }
        }
    }
}
