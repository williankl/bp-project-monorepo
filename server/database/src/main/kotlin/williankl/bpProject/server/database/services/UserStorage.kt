package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.User

public interface UserStorage {
    public suspend fun createUser(user: User)
    public suspend fun retrieveUser(
        credential: String
    ): User?

    public suspend fun userEncryptedPassword(
        id: Uuid
    ): String?

    public suspend fun findUserByBearer(
        token: String
    ): User?
}
