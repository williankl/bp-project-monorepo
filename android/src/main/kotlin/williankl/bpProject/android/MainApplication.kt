package williankl.bpProject.android

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import williankl.bpProject.common.core.commonCoreDi
import williankl.bpProject.common.data.auth.authServiceDi
import williankl.bpProject.common.data.imageRetrievalService.imageRetrievalServiceDi
import williankl.bpProject.common.data.networking.networkingDi
import williankl.bpProject.common.data.placeService.placesServiceDi
import williankl.bpProject.common.features.authentication.authenticationFeatureDi

internal class MainApplication : Application(), DIAware {
    override val di: DI = DI {
        import(androidCoreModule(this@MainApplication))
        import(androidXModule(this@MainApplication))
        import(commonCoreDi)
        import(networkingDi)
        import(authServiceDi)
        import(placesServiceDi)
        import(authenticationFeatureDi)
        import(imageRetrievalServiceDi)
    }
}
