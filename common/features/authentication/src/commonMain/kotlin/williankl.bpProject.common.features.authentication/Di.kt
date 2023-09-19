package williankl.bpProject.common.features.authentication

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

public val authenticationFeatureDi: DI.Module = DI.Module("williankl.bpProject.common.features.authentication") {
    bindProvider {
        AuthenticationRunnerModel(
            authService = instance(),
            preferencesHandler = instance(),
            session = instance(),
            dispatcher = Dispatchers.Default,
        )
    }
}
