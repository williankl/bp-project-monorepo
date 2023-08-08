package williankl.bpProject.server.app.configuration

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

internal object ConfigurationRunner {

    fun Application.configure(){
        install(ContentNegotiation){
            json()
        }
    }
}