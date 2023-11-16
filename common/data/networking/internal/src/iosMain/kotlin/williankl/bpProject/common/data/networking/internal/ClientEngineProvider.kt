package williankl.bpProject.common.data.networking.internal

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

internal actual class ClientEngineProvider {
    actual fun provide(): HttpClientEngineFactory<*> = Darwin
}
