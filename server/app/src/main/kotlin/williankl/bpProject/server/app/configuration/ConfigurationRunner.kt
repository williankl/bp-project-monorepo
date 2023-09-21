package williankl.bpProject.server.app.configuration

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import williankl.bpProject.server.app.configuration.AuthenticationHandler.bearerConfig
import williankl.bpProject.server.app.serverDi

internal object ConfigurationRunner {

    private val json by serverDi.instance<Json>()

    fun Application.configure() {
        installAuthentication()
        installContentNegotiation()
    }

    private fun Application.installAuthentication() {
        install(Authentication) {
            bearerConfig()
        }
    }

    private fun Application.installContentNegotiation() {
        install(ContentNegotiation) {
            json(json)
        }
    }
}
