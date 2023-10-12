package williankl.bpProject.server.app

import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.core.commonCoreDi
import williankl.bpProject.common.data.cypher.cypherDi
import williankl.bpProject.common.data.networking.NetworkConstant
import williankl.bpProject.common.data.networking.networkingDi
import williankl.bpProject.common.data.placeService.models.MapServiceType
import williankl.bpProject.common.data.placeService.placesServiceDi
import williankl.bpProject.server.app.routing.MasterRouter
import williankl.bpProject.server.app.routing.auth.AuthRouter
import williankl.bpProject.server.app.routing.maps.MapsRouter
import williankl.bpProject.server.app.routing.places.PlaceRouter
import williankl.bpProject.server.app.routing.places.RatingRouter
import williankl.bpProject.server.app.routing.user.UserRouter
import williankl.bpProject.server.database.serverDatabaseDi

internal val serverDi = DI {
    import(commonCoreDi)
    import(cypherDi)
    import(networkingDi)
    import(placesServiceDi)
    import(serverDatabaseDi)

    bindConstant(NetworkConstant.GooglePlacesApiKey) {
        retrieveFromEnv("MAPS_API_KEY")
            ?: error("Could not retrieve maps key from environment")
    }

    bindSingleton {
        listOf(
            MapsRouter(
                mapsService = instance(MapServiceType.Server)
            ),
            AuthRouter(
                authStorage = instance(),
                cypher = instance(),
            ),
            PlaceRouter(
                placeStorage = instance(),
                userStorage = instance(),
            ),
            RatingRouter(
                placesRatingStorage = instance(),
            ),
            UserRouter(
                userStorage = instance(),
            ),
        )
    }

    bindSingleton {
        MasterRouter(
            routes = instance()
        )
    }
}
