package williankl.bpProject.common.data.firebaseIntegration.internal

import korlibs.image.bitmap.Bitmap
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.sessionHandler.Session

internal actual class FirebaseStorageInfrastructure actual constructor(
    private val session: Session,
) : FirebaseIntegration {
    override suspend fun uploadPlacesImages(
        images: List<Bitmap>
    ): List<String> {
        TODO("Not yet implemented")
    }
}
