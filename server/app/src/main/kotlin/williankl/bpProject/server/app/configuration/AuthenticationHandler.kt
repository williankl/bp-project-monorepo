package williankl.bpProject.server.app.configuration

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import org.kodein.di.instance
import williankl.bpProject.server.app.serverDi
import williankl.bpProject.server.database.services.UserStorage

internal object AuthenticationHandler {

    private val userStorage by serverDi.instance<UserStorage>()
    internal const val BEARER_KEY = "bearer-provider"

    fun AuthenticationConfig.bearerConfig() {
        bearer(BEARER_KEY) {
            authenticate { tokenCredential ->
                userStorage.findUserByBearer(tokenCredential.token)
                    ?.let { user ->
                        UserIdPrincipal(user.id.toString())
                    }
            }
        }
    }
}
