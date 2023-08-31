package williankl.bpProject.server.database.internal.user

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import user.UserBearerCredential
import user.UserCredentials
import user.UserData
import williankl.bpProject.common.core.models.User
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.user.Mapper.fromDomain
import williankl.bpProject.server.database.internal.user.Mapper.toDomain
import williankl.bpProject.server.database.services.UserStorage
import java.util.Date

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

    override suspend fun createUser(
        user: User,
        encryptedPassword: String,
    ) {
        withDatabase(driver) {
            userDataQueries.createFullUser(fromDomain(user))
            userCredentialsQueries.createPassword(
                UserCredentials(
                    ownerId = user.id,
                    encryptedPassword = encryptedPassword,
                )
            )
        }
    }

    override suspend fun retrieveUser(credential: String): User? =
        findUserForCredential(credential)
            ?.let(::toDomain)

    override suspend fun retrieveUser(id: Uuid): User? {
        return withDatabase(driver) {
            userDataQueries.findUserById(id)
                .executeAsOneOrNull()
                ?.let(::toDomain)
        }
    }

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

    override suspend fun attachBearerToken(userId: Uuid, token: String) {
        withDatabase(driver) {
            userBearerCredentialQueries.attachToken(
                UserBearerCredential(
                    ownerId = userId,
                    createdAt = Date().time,
                    valid = true,
                    token = token,
                )
            )
        }
    }

    override suspend fun findUserByBearer(token: String): User? {
        return withDatabase(driver) {
            val bearerCredential = userBearerCredentialQueries.findBearerCredentialByToken(token)
                .executeAsList()
                .maxByOrNull { credential -> credential.createdAt }

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
