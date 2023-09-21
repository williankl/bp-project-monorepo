package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.User

public interface UserStorage {

    public suspend fun retrieveUser(
        id: Uuid
    ): User?

    public suspend fun findUserByBearer(
        token: String
    ): User?
}
