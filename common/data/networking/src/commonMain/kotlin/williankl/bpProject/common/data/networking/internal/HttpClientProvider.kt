package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClient

internal expect class HttpClientProvider(
    configurationHelper: ClientConfigurationHelper
) {
    fun provideBpClient(): HttpClient

    fun provideGoogleClient(): HttpClient
}
