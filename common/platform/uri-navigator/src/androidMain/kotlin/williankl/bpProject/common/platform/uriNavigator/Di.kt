package williankl.bpProject.common.platform.uriNavigator

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

public actual val uriNavigatorDi: DI.Module =
    DI.Module("williankl.bpProject.common.platform.uriNavigator.android") {
        bindSingleton {
            UriNavigator(
                context = instance()
            )
        }
    }
