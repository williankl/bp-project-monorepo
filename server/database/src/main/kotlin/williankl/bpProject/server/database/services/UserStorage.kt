package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.User

public interface UserStorage {
    public suspend fun createUser(
        user: User,
        encryptedPassword: String,
    )

    public suspend fun retrieveUser(
        credential: String
    ): User?

    public suspend fun retrieveUser(
        id: Uuid
    ): User?

    public suspend fun userEncryptedPassword(
        id: Uuid
    ): String?

    public suspend fun attachBearerToken(
        userId: Uuid,
        token: String,
    )

    public suspend fun invalidateBearerToken(
        token: String,
    )

    public suspend fun findUserByBearer(
        token: String
    ): User?
}
