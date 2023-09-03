package williankl.bpProject.common.data.networking

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.internal.ClientConfigurationHelper
import williankl.bpProject.common.data.networking.internal.HttpClientProvider

public val networkingDi: DI.Module = DI.Module("williankl.bpProject.common.data.networking") {
    bindSingleton<ClientConfigurationHelper> {
        ClientConfigurationHelper(
            bpBaseUrl = "http://10.0.2.2:8080/", // fixme - localhost for testing
            googlePlacesBaseUrl = "https://places.googleapis.com", // fixme - place this in a better place
            json = instance(),
        )
    }

    bindSingleton<HttpClient> {
        HttpClientProvider(
            configurationHelper = instance()
        ).provideBpClient()
    }

    bindSingleton<HttpClient>(ClientType.BeautifulPlaces) {
        HttpClientProvider(
            configurationHelper = instance()
        ).provideBpClient()
    }

    bindSingleton<HttpClient>(ClientType.GooglePlaces) {
        HttpClientProvider(
            configurationHelper = instance()
        ).provideGoogleClient()
    }
}
