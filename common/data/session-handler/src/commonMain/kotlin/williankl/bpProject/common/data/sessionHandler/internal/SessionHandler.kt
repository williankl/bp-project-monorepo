package williankl.bpProject.common.data.sessionHandler.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.data.sessionHandler.Session

internal class SessionHandler(
    private val client: HttpClient,
): Session {

    private companion object {
        const val USER_ENDPOINT = "/user"
    }
    override suspend fun loggedInUser(): User? {
        return client.get(USER_ENDPOINT).body()
    }

    override suspend fun logOut() {
        client.delete(USER_ENDPOINT)
    }
}