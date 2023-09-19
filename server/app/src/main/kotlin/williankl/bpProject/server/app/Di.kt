package williankl.bpProject.server.app

import org.kodein.di.DI
import williankl.bpProject.common.data.cypher.cypherDi
import williankl.bpProject.server.database.serverDatabaseDi

internal val serverDi = DI {
    import(cypherDi)
    import(serverDatabaseDi)
}
