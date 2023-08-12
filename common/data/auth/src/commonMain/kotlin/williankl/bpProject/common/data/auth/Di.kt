package williankl.bpProject.common.data.auth

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

public val authDataDi: DI.Module = DI.Module("williankl.bpProject.common.data.auth") {
    bindSingleton<AuthService> {
        AuthInfrastructure(
            json = instance(),
            client = instance(),
        )
    }
}