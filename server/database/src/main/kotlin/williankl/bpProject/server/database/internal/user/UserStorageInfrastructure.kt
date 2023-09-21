package williankl.bpProject.server.database.internal.user

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.User
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.user.Mapper.toDomain
import williankl.bpProject.server.database.services.UserStorage

internal class UserStorageInfrastructure(
    private val driver: JdbcDriver,
) : UserStorage {

    override suspend fun retrieveUser(id: Uuid): User? {
        return withDatabase(driver) {
            userDataQueries.findUserById(id)
                .executeAsOneOrNull()
                ?.let(::toDomain)
        }
    }

    override suspend fun findUserByBearer(token: String): User? {
        return withDatabase(driver) {
            val bearerCredentials = userBearerCredentialQueries.findBearerCredentialByToken(token)
                .executeAsList()

            bearerCredentials
                .firstOrNull { credential -> token == credential.token && credential.valid }
                ?.ownerId
                ?.let { ownerId ->
                    userDataQueries.findUserById(ownerId)
                        .executeAsOneOrNull()
                        ?.let(::toDomain)
                }
        }
    }
}
