package williankl.bpProject.common.data.networking

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.internal.ClientConfigurationHelper
import williankl.bpProject.common.data.networking.internal.ClientEngineProvider
import williankl.bpProject.common.data.networking.internal.HttpClientProvider

public val networkingDi: DI.Module = DI.Module("williankl.bpProject.common.data.networking") {
    bindSingleton<ClientConfigurationHelper> {
        ClientConfigurationHelper(
            bpBaseUrl = instance(NetworkConstant.BeautifulPlacesBaseUrl),
            googlePlacesBaseUrl = instance(NetworkConstant.GooglePlacesBaseUrl),
            googleMapsBaseUrl = instance(NetworkConstant.GoogleMapsBaseUrl),
            googleMapsKey = instance(NetworkConstant.GooglePlacesApiKey),
            preferencesHandler = instance(),
            json = instance(),
        )
    }

    bindSingleton {
        ClientEngineProvider()
    }

    bindSingleton<HttpClient> {
        HttpClientProvider(
            configurationHelper = instance(),
            engineProvider = instance(),
        ).provideBpClient()
    }

    bindSingleton<HttpClient>(ClientType.GooglePlaces) {
        HttpClientProvider(
            configurationHelper = instance(),
            engineProvider = instance(),
        ).provideGooglePlacesClient()
    }

    bindSingleton<HttpClient>(ClientType.GoogleMaps) {
        HttpClientProvider(
            configurationHelper = instance(),
            engineProvider = instance(),
        ).provideGoogleMapsClient()
    }
}
