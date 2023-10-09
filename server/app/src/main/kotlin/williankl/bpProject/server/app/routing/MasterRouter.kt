package williankl.bpProject.server.app.routing

import io.ktor.server.application.Application
import io.ktor.server.routing.routing

internal class MasterRouter(
    private val routes: List<BPRoute>,
) {
    context (Application)
    fun applyAppRoutes() {
        routing {
            routes.forEach { route -> route.route() }
        }
    }
}
