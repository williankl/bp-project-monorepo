package williankl.bpProject.common.data.networking

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.internal.ClientConfigurationHelper
import williankl.bpProject.common.data.networking.internal.HttpClientProvider


public val networkingDi: DI.Module = DI.Module("williankl.bpProject.common.data.networking") {
    bindSingleton<ClientConfigurationHelper> {
        ClientConfigurationHelper(
            json = instance()
        )
    }

    bindSingleton<HttpClient> {
        HttpClientProvider(
            configurationHelper = instance()
        ).provide()
    }
}