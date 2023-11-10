package williankl.bpProject.android

import android.app.Application
import dev.icerock.moko.maps.google.GoogleMapController
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindConstant
import org.kodein.di.bindProvider
import williankl.bpProject.common.application.applicationDi
import williankl.bpProject.common.data.networking.NetworkConstant

internal class MainApplication : Application(), DIAware {
    override val di: DI = DI {
        import(applicationDi)
        import(androidCoreModule(this@MainApplication))
        import(androidXModule(this@MainApplication))

        bindConstant(NetworkConstant.GooglePlacesApiKey) {
            BuildConfig.MAPS_API_KEY
        }

        bindProvider {
            GoogleMapController(BuildConfig.MAPS_API_KEY)
        }
    }
}
