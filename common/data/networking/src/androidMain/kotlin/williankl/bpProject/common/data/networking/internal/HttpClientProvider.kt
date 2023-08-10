package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.json.Json

internal actual class HttpClientProvider actual constructor(
    private val configurationHelper: ClientConfigurationHelper
) {
    actual fun provide(): HttpClient {
        return HttpClient(OkHttp) {
            with(configurationHelper) {
                commonConfiguration()
            }
        }
    }
}