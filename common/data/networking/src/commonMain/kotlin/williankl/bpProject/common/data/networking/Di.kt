package williankl.bpProject.common.data.networking

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.internal.ClientConfigurationHelper
import williankl.bpProject.common.data.networking.internal.HttpClientProvider

public val networkingDi: DI.Module = DI.Module("williankl.bpProject.common.data.networking") {
    bindSingleton<ClientConfigurationHelper> {
        ClientConfigurationHelper(
            baseUrl = "http://127.0.0.1:8080/", // fixme - localhost for testing
            json = instance(),
        )
    }

    bindSingleton<HttpClient> {
        HttpClientProvider(
            configurationHelper = instance()
        ).provide()
    }
}
