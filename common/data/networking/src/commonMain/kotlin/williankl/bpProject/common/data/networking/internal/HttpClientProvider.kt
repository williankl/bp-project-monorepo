package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClient

internal class HttpClientProvider(
    private val configurationHelper: ClientConfigurationHelper,
    private val engineProvider: ClientEngineProvider,
) {
    fun provideBpClient(): HttpClient {
        return HttpClient(engineProvider.provide()) {
            with(configurationHelper) {
                commonConfiguration()
            }
        }
    }

    fun provideGooglePlacesClient(): HttpClient {
        return HttpClient(engineProvider.provide()) {
            with(configurationHelper) {
                googlePlacesConfiguration()
            }
        }
    }

    fun provideGoogleMapsClient(): HttpClient {
        return HttpClient(engineProvider.provide()) {
            with(configurationHelper) {
                googleMapsConfiguration()
            }
        }
    }
}
