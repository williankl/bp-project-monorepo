package williankl.bpProject.common.data.firebaseIntegration

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.firebaseIntegration.internal.FirebaseStorageInfrastructure

public val firebaseIntegrationDi: DI.Module = DI.Module("williankl.bpProject.common.data.firebaseIntegration") {

    bindSingleton<FirebaseIntegration> {
        FirebaseStorageInfrastructure(
            session = instance(),
        )
    }
}
