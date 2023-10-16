package williankl.bpProject.server.routing.core

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import williankl.bpProject.server.core.BPRoute

public class MasterRouter(
    private val routes: List<BPRoute>,
) {
    context (Application)
    public fun applyAppRoutes() {
        routing {
            routes.forEach { route -> route.route() }
        }
    }
}
