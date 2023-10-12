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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import williankl.bpProject.common.data.networking.NetworkClientSession

internal class ClientConfigurationHelper(
    private val bpBaseUrl: String,
    private val googlePlacesBaseUrl: String,
    private val googleMapsBaseUrl: String,
    private val googleMapsKey: String,
    private val networkClientSession: NetworkClientSession,
    private val json: Json,
) {

    private companion object {
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
        const val BEARER_HEADER_KEY = "Bearer"
        const val MAPS_HEADER_KEY = "X-Goog-Api-Key"
        const val MAPS_PARAMETER_KEY = "key"
    }

    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.commonConfiguration() {
        defaultConfigWithUrl(bpBaseUrl) {
            bearerAuth()
        }
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
            contentType(ContentType.Application.Json)
            url(url)
            onDefaultRequest()
        }
    }

    private fun DefaultRequest.DefaultRequestBuilder.bearerAuth() {
        networkClientSession.currentToken()
            ?.let { token ->
                headers.append(AUTHORIZATION_HEADER_KEY, "$BEARER_HEADER_KEY $token")
            }
    }
}
