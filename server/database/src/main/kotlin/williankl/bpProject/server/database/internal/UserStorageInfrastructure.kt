package williankl.bpProject.server.database.internal

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import user.UserData
import williankl.bpProject.server.database.UserStorage
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase

internal class UserStorageInfrastructure(
    private val driver: JdbcDriver,
) : UserStorage {
    override suspend fun createUser(userData: UserData) {
        withDatabase(driver) {
            userDataQueries.createTableIfNeeded()
            userDataQueries.createFullUser(userData)
        }
    }

    override suspend fun retrieveUser(
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