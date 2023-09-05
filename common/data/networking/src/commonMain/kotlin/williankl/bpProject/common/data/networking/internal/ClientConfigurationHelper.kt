package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ClientConfigurationHelper(
    private val bpBaseUrl: String,
    private val googlePlacesBaseUrl: String,
    private val googleMapsBaseUrl: String,
    private val googleMapsKey: String,
    private val json: Json,
) {

    private companion object {
        const val MAPS_HEADER_KEY = "X-Goog-Api-Key"
        const val MAPS_PARAMETER_KEY = "key"
    }

    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.commonConfiguration() {
        defaultConfigWithUrl(bpBaseUrl)
    }

    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.googlePlacesConfiguration() {
        defaultConfigWithUrl(googlePlacesBaseUrl) {
            headers.append(MAPS_HEADER_KEY, googleMapsKey)
        }
    }

    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.googleMapsConfiguration() {
        defaultConfigWithUrl(googleMapsBaseUrl) {
            url.parameters.append(MAPS_PARAMETER_KEY, googleMapsKey)
        }
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.defaultConfigWithUrl(
        url: String,
        onDefaultRequest: DefaultRequest.DefaultRequestBuilder.() -> Unit = { /* Nothing by default */ },
    ) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(DefaultRequest) {
            url(url)
            contentType(ContentType.Application.Json)
            onDefaultRequest()
        }
    }
}
