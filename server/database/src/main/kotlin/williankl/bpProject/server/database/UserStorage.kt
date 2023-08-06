package williankl.bpProject.server.database

import user.UserData

public interface UserStorage {
    public suspend fun createUser(userData: UserData)
    public suspend fun retrieveUser(
        email: String? = null,
        tag: String? = null,
    ): UserData?
}