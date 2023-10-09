package williankl.bpProject.common.data.networking.internal

import io.ktor.client.engine.HttpClientEngineFactory

internal expect class ClientEngineProvider() {
    fun provide(): HttpClientEngineFactory<*>
}
