package williankl.bpProject.server.database

import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.singleton
import williankl.bpProject.server.database.internal.DriverProvider
import williankl.bpProject.server.database.internal.user.UserStorageInfrastructure

public val serverDatabaseDi: DI.Module = DI.Module("williankl.bpProject.server.database") {
    singleton {
        DriverProvider.provideDriver()
    }

    singleton {
        UserStorageInfrastructure(
            driver = instance()
        )
    }
}