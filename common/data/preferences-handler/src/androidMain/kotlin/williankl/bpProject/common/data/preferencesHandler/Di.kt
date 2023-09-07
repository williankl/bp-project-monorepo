package williankl.bpProject.common.data.preferencesHandler

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

public actual val preferencesHandlerDi: DI.Module =
    DI.Module("williankl.bpProject.common.data.preferencesHandler") {
        bindSingleton<PreferencesHandler> {
            PreferencesHandlerInfrastructure(
                context = instance()
            )
        }
    }
