package williankl.bpProject.server.app

import io.ktor.server.routing.Route
import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.core.commonCoreDi
import williankl.bpProject.common.data.cypher.cypherDi
import williankl.bpProject.common.data.networking.NetworkConstant
import williankl.bpProject.common.data.networking.networkingDi
import williankl.bpProject.common.data.placeService.placesServiceDi
import williankl.bpProject.server.core.BPRoute
import williankl.bpProject.server.core.Routers
import williankl.bpProject.server.database.serverDatabaseDi
import williankl.bpProject.server.routing.core.routingCoreDi
import williankl.bpProject.server.core.retrieveFromEnv
import williankl.bpProject.server.routing.core.MasterRouter

internal val serverDi = DI {
    import(commonCoreDi)
    import(cypherDi)
    import(networkingDi)
    import(placesServiceDi)

    import(routingCoreDi)
    import(serverDatabaseDi)

    bindConstant(NetworkConstant.GooglePlacesApiKey) {
        retrieveFromEnv("MAPS_API_KEY")
            ?: error("Could not retrieve maps key from environment")
    }

    bindSingleton {
        MasterRouter(
            routes = instance<List<BPRoute>>(Routers.Core) +
                    instance<List<BPRoute>>(Routers.BFF)
        )
    }
}
