package williankl.bpProject.server.database.internal

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import williankl.bpProject.common.core.runOrNull
import williankl.bpProject.server.database.BpProject
import java.util.Properties

internal object DriverProvider {

    private val propertiesConfiguration: HikariConfig by lazy {
        runOrNull {
            HikariConfig("hikari.properties")
        } ?: HikariConfig(propertiesFromEnvironment())
    }

    fun provideDriver(): JdbcDriver {
        HikariConfig()
        val dataSource = HikariDataSource(propertiesConfiguration)
        return dataSource.asJdbcDriver()
    }

    fun <T> withDatabase(
        driver: JdbcDriver,
        action: BpProject.() -> T
    ): T {
        return with(
            BpProject.invoke(driver)
        ) {
            action()
        }
    }

    private fun propertiesFromEnvironment(): Properties {
        return Properties().apply {
            setProperty("dataSourceClassName", System.getenv("DB_SOURCE_CLASS"))
            setProperty("dataSource.user", System.getenv("DB_USER"))
            setProperty("dataSource.password", System.getenv("DB_PASSWORD"))
            setProperty("dataSource.databaseName", System.getenv("DB_DATABASE"))
            setProperty("dataSource.serverName", System.getenv("DB_SERVER"))
        }
    }
}
