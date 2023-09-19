package williankl.bpProject.common.data.sessionHandler

import williankl.bpProject.common.core.models.User

public interface Session {
    public suspend fun loggedInUser(
        refreshSession: Boolean = false
    ): User?

    public suspend fun clearSession()
}
