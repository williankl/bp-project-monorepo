package williankl.bpProject.server.database.services

import williankl.bpProject.common.core.models.User

public interface UserStorage {
    public suspend fun createUser(user: User)
    public suspend fun retrieveUser(
        email: String? = null,
        tag: String? = null,
    ): User?

    public suspend fun userEncryptedPassword(
        email: String? = null,
        tag: String? = null,
    ): String?
}
