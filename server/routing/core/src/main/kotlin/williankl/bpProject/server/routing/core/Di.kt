package williankl.bpProject.server.routing.core

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.placeService.models.MapServiceType
import williankl.bpProject.server.core.Routers
import williankl.bpProject.server.routing.core.auth.AuthRouter
import williankl.bpProject.server.routing.core.maps.MapsRouter
import williankl.bpProject.server.routing.core.places.PlaceRouter
import williankl.bpProject.server.routing.core.places.RatingRouter
import williankl.bpProject.server.routing.core.user.UserRouter

public val routingCoreDi: DI.Module = DI.Module("williankl.bpProject.server.routing.core") {
    bindSingleton(Routers.Core) {
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
}
