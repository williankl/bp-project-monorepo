package williankl.bpProject.server.core

import io.ktor.server.routing.Route

public interface BPRoute {
    context (Route)
    public fun route()
}
