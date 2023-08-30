package williankl.bpProject.server.app.configuration

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import williankl.bpProject.server.app.configuration.AuthenticationHandler.basicConfig
import williankl.bpProject.server.app.configuration.AuthenticationHandler.bearerConfig

internal object ConfigurationRunner {

    fun Application.configure() {
        installAuthentication()
        installContentNegotiation()
    }

    private fun Application.installAuthentication() {
        install(Authentication) {
            basicConfig()
            bearerConfig()
        }
    }

    private fun Application.installContentNegotiation() {
        install(ContentNegotiation) {
            json()
        }
    }
}
