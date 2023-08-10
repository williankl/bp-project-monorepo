package williankl.bpProject.server.database.internal.user

import app.cash.sqldelight.driver.jdbc.JdbcDriver
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
        }
    }

    override suspend fun createUser(user: User) {
        withDatabase(driver) {
            userDataQueries.createFullUser(fromDomain(user))
        }
    }

    override suspend fun retrieveUser(
        email: String?,
        tag: String?,
    ): User? = findUserForEmailOrTag(email, tag)
        ?.let(::toDomain)

    override suspend fun userEncryptedPassword(
        email: String?,
        tag: String?,
    ): String? {
        val user = findUserForEmailOrTag(email, tag)
        return withDatabase(driver) {
            userCredentialsQueries.createTableIfNeeded()
            user?.id?.let {
                userCredentialsQueries
                    .findCredentialsById(user.id)
                    .executeAsOneOrNull()
                    ?.encryptedPassword
            }
        }
    }

    private fun findUserForEmailOrTag(
        email: String?,
        tag: String?,
    ): UserData? {
        return withDatabase(driver) {
            val query = email
                ?.let(userDataQueries::findUserByEmail)
                ?: tag?.let(userDataQueries::findUserByTag)

            query
                ?.executeAsList()
                ?.firstOrNull()
        }
    }
}
