package williankl.bpProject.server.app

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import io.ktor.server.routing.routing
import org.kodein.di.instance
import williankl.bpProject.server.app.configuration.ConfigurationRunner.configure
import williankl.bpProject.server.routing.core.MasterRouter

public fun main(args: Array<String>): Unit = EngineMain.main(args)

public fun Application.module() {
    val masterRoute by serverDi.instance<MasterRouter>()
    configure()
    routing {
        masterRoute.applyAppRoutes()
    }
}
