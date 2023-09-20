package williankl.bpProject.common.data.sessionHandler.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.data.sessionHandler.PreferencesHandler
import williankl.bpProject.common.data.sessionHandler.Session

internal class SessionHandler(
    private val client: HttpClient,
    private val preferencesHandler: PreferencesHandler,
) : Session {

    private companion object {
        const val USER_ENDPOINT = "/user"
    }

    private val currentCachedUser = MutableStateFlow<User?>(null)

    override suspend fun loggedInUser(
        refreshSession: Boolean,
    ): User? {
        currentCachedUser.value =
            if (currentCachedUser.value == null || refreshSession) client.get(USER_ENDPOINT).body()
            else currentCachedUser.value

        return currentCachedUser.value
    }

    override suspend fun clearSession() {
        currentCachedUser.value = null
        preferencesHandler.clearPreferences()
    }
}
