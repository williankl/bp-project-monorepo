package williankl.bpProject.server.app

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import williankl.bpProject.server.app.configuration.ConfigurationRunner.configure
import williankl.bpProject.server.app.routing.places.PlaceRouter.placesRoute

public fun main(args: Array<String>): Unit = EngineMain.main(args)

public fun Application.module() {
    configure()
    routing {
        placesRoute()
    }
}
