package williankl.bpProject.common.data.auth

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

public val authServiceDi: DI.Module = DI.Module("williankl.bpProject.common.data.auth") {
    bindSingleton<AuthService> {
        AuthInfrastructure(
            json = instance(),
            client = instance(),
        )
    }
}