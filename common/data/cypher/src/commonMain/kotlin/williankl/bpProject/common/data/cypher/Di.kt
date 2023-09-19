package williankl.bpProject.common.data.cypher

import org.kodein.di.DI
import org.kodein.di.bindSingleton

public val cypherDi: DI.Module = DI.Module("williankl.bpProject.common.data.cypher") {
    bindSingleton {
        BeautifulCypher()
    }
}
