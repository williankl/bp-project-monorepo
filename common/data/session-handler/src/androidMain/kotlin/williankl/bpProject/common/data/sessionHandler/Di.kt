package williankl.bpProject.common.data.sessionHandler

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal actual val platformSessionHandlerDi: DI.Module =
    DI.Module("williankl.bpProject.common.data.sessionHandler.android") {
        bindSingleton<PreferencesHandler> {
            PreferencesHandlerInfrastructure(
                context = instance()
            )
        }
    }
