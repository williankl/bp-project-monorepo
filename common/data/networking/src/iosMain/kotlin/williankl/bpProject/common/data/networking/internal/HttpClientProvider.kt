package williankl.bpProject.common.data.networking.internal

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal actual class HttpClientProvider actual constructor(
    private val configurationHelper: ClientConfigurationHelper
) {
    actual fun provideBpClient(): HttpClient {
        return HttpClient(Darwin) {
            with(configurationHelper) {
                commonConfiguration()
            }
        }
    }
}
