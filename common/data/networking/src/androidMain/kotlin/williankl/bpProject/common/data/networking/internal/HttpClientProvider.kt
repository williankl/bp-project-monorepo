package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual class HttpClientProvider actual constructor(
    private val configurationHelper: ClientConfigurationHelper
) {
    actual fun provideBpClient(): HttpClient {
        return HttpClient(OkHttp) {
            with(configurationHelper) {
                commonConfiguration()
            }
        }
    }

    actual fun provideGooglePlacesClient(): HttpClient {
        return HttpClient(OkHttp) {
            with(configurationHelper) {
                googlePlacesConfiguration()
            }
        }
    }

    public actual fun provideGoogleMapsClient(): HttpClient {
        return HttpClient(OkHttp) {
            with(configurationHelper) {
                googleMapsConfiguration()
            }
        }
    }
}
