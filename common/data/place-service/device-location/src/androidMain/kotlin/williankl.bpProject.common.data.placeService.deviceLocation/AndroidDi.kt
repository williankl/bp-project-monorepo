package williankl.bpProject.common.data.placeService.deviceLocation

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal actual val platformDi: DI.Module =
    DI.Module("williankl.bpProject.common.data.placeService.deviceLocation.android") {
        bindSingleton {
            UserLocationRetriever(
                context = instance()
            )
        }
    }
