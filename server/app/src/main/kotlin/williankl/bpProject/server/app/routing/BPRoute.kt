package williankl.bpProject.server.app.routing

import io.ktor.server.routing.Route

internal interface BPRoute {
    context (Route)
    suspend fun route()
}