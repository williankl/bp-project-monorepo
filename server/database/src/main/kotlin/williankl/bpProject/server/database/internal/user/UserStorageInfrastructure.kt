package williankl.bpProject.server.database.internal.user

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import user.UserData
import williankl.bpProject.common.core.models.User
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.user.Mapper.fromDomain
import williankl.bpProject.server.database.internal.user.Mapper.toDomain
import williankl.bpProject.server.database.services.UserStorage

internal class UserStorageInfrastructure(
    private val driver: JdbcDriver,
) : UserStorage {

    init {
        withDatabase(driver) {
            userDataQueries.createTableIfNeeded()
            userCredentialsQueries.createTableIfNeeded()
            userBearerCredentialQueries.createTableIfNeeded()
        }
    }

    override suspend fun createUser(user: User) {
        withDatabase(driver) {
            userDataQueries.createFullUser(fromDomain(user))
        }
    }

    override suspend fun retrieveUser(credential: String): User? =
        findUserForCredential(credential)
            ?.let(::toDomain)

    override suspend fun userEncryptedPassword(id: Uuid): String? {
        return withDatabase(driver) {
            val user = userDataQueries.findUserById(id)
                .executeAsOneOrNull()

            user?.let {
                userCredentialsQueries
                    .findCredentialsById(user.id)
                    .executeAsOneOrNull()
                    ?.encryptedPassword
            }
        }
    }

    override suspend fun findUserByBearer(token: String): User? {
        return withDatabase(driver) {
            val bearerCredential = userBearerCredentialQueries.findBearerCredentialByToken(token)
                .executeAsList()
                .lastOrNull()

            bearerCredential?.let { credential ->
                userDataQueries.findUserById(credential.ownerId)
                    .executeAsOneOrNull()
                    ?.let(::toDomain)
            }
        }
    }

    private fun findUserForCredential(
        credential: String
    ): UserData? {
        return withDatabase(driver) {
            userDataQueries
                .findUserByEmail(credential)
                .executeAsOneOrNull()
                ?: userDataQueries
                    .findUserByTag(credential)
                    .executeAsOneOrNull()
        }
    }
}
