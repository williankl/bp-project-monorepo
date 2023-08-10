package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ClientConfigurationHelper(
    private val baseUrl: String,
    private val json: Json,
) {
    fun <T : HttpClientEngineConfig> HttpClientConfig<T>.commonConfiguration() {
        install(Logging)
        install(ContentNegotiation) {
            json(json)
        }
        install(DefaultRequest) {
            url(baseUrl)
        }
    }
}
