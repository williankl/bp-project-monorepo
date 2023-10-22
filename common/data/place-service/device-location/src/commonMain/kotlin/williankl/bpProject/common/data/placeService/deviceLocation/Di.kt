package williankl.bpProject.common.data.placeService.deviceLocation

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.placeService.services.UserLocationService

internal expect val platformDi: DI.Module

public val deviceLocationDi: DI.Module = DI.Module("williankl.bpProject.common.data.placeService.deviceLocation") {
    import(platformDi)

    bindSingleton<UserLocationService> {
        instance<UserLocationRetriever>()
    }
}
