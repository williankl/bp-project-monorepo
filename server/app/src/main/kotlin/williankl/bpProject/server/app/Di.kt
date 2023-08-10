package williankl.bpProject.server.app

import org.kodein.di.DI
import williankl.bpProject.server.database.serverDatabaseDi

internal val serverDi = DI {
    import(serverDatabaseDi)
}
