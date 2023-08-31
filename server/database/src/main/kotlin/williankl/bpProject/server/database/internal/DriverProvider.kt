package williankl.bpProject.server.database.internal

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuidFrom
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.UUID
import place.PlaceData
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
