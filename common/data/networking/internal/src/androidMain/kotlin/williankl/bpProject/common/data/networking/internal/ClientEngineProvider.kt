package williankl.bpProject.common.data.networking.internal

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

internal actual class ClientEngineProvider {
    actual fun provide(): HttpClientEngineFactory<*> = OkHttp
}
