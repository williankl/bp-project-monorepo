package williankl.bpProject.server.database

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.server.database.internal.DriverProvider
import williankl.bpProject.server.database.internal.auth.AuthenticationStorageInfrastructure
import williankl.bpProject.server.database.internal.place.PlaceStorageInfrastructure
import williankl.bpProject.server.database.internal.place.RatingStorageInfrastructure
import williankl.bpProject.server.database.internal.user.UserStorageInfrastructure
import williankl.bpProject.server.database.services.AuthStorage
import williankl.bpProject.server.database.services.PlaceRatingStorage
import williankl.bpProject.server.database.services.PlaceStorage
import williankl.bpProject.server.database.services.UserStorage

public val serverDatabaseDi: DI.Module = DI.Module("williankl.bpProject.server.database") {
    bindSingleton {
        DriverProvider.provideDriver()
    }

    bindSingleton<UserStorage> {
        UserStorageInfrastructure(
            driver = instance()
        )
    }

    bindSingleton<AuthStorage> {
        AuthenticationStorageInfrastructure(
            driver = instance()
        )
    }

    bindSingleton<PlaceStorage> {
        PlaceStorageInfrastructure(
            driver = instance()
        )
    }

    bindSingleton<PlaceRatingStorage> {
        RatingStorageInfrastructure(
            driver = instance()
        )
    }
}
