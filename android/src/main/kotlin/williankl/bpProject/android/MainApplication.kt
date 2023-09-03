package williankl.bpProject.android

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindConstant
import williankl.bpProject.common.core.commonCoreDi
import williankl.bpProject.common.data.auth.authServiceDi
import williankl.bpProject.common.data.firebaseIntegration.firebaseIntegrationDi
import williankl.bpProject.common.data.imageRetrievalService.imageRetrievalServiceDi
import williankl.bpProject.common.data.networking.NetworkConstant
import williankl.bpProject.common.data.networking.networkingDi
import williankl.bpProject.common.data.placeService.placesServiceDi
import williankl.bpProject.common.features.authentication.authenticationFeatureDi
import williankl.bpProject.common.features.dashboard.dashboardDi
import williankl.bpProject.common.features.places.placesDi

internal class MainApplication : Application(), DIAware {
    override val di: DI = DI {
        import(androidCoreModule(this@MainApplication))
        import(androidXModule(this@MainApplication))
        import(commonCoreDi)
        import(imageRetrievalServiceDi)
        import(firebaseIntegrationDi)
        import(networkingDi)
        import(authServiceDi)
        import(placesServiceDi)
        import(authenticationFeatureDi)
        import(dashboardDi)
        import(placesDi)

        bindConstant(NetworkConstant.GooglePlacesApiKey) {
            BuildConfig.MAPS_API_KEY
        }

        bindConstant(NetworkConstant.GooglePlacesBaseUrl) {
            "https://places.googleapis.com/"
        }

        bindConstant(NetworkConstant.BeautifulPlacesBaseUrl) {
            "http://127.0.0.1:8080/"
        }
    }
}
