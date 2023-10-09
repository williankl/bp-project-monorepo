package williankl.bpProject.common.data.firebaseIntegration.internal

import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.sessionHandler.Session

internal expect class FirebaseStorageInfrastructure(
    session: Session
) : FirebaseIntegration
