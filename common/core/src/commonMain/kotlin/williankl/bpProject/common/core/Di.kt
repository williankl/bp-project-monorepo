package williankl.bpProject.common.core

import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton

public val commonCoreDi: DI.Module = DI.Module("williankl.bpProject.common.core") {
    bindSingleton {
        Json {
            ignoreUnknownKeys = true
        }
    }
}