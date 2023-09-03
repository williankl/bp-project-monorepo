package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ClientConfigurationHelper(
    private val bpBaseUrl: String,
    private val googlePlacesBaseUrl: String,
    private val json: Json,
) {
    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.commonConfiguration() {
        defaultConfigWithUrl(bpBaseUrl)
    }

    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.googleConfiguration() {
        defaultConfigWithUrl(googlePlacesBaseUrl)
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.defaultConfigWithUrl(url: String){
        install(Logging)
        install(ContentNegotiation) {
            json(json)
        }
        install(DefaultRequest) {
            url(url)
            contentType(ContentType.Application.Json)
        }
    }
}
