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
            val bearerCredential = userBearerCredentialQueries.findBearerCredentialByToken(token)
                .executeAsList()
                .maxByOrNull { credential -> credential.createdAt }

            bearerCredential?.let { credential ->
                if (credential.valid) {
                    userDataQueries.findUserById(credential.ownerId)
                        .executeAsOneOrNull()
                        ?.let(::toDomain)
                } else null
            }
        }
    }
}
