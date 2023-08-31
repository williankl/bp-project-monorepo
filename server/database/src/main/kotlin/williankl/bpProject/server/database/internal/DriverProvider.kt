package williankl.bpProject.server.database.internal

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import williankl.bpProject.server.database.BpProject

internal object DriverProvider {
    fun provideDriver(): JdbcDriver {
        val config = HikariConfig("hikari.properties")
        val dataSource = HikariDataSource(config)
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
}
