package williankl.bpProject.common.data.firebaseIntegration.internal

import dev.icerock.moko.media.Bitmap
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.firebaseIntegration.models.ImageUploadResult
import williankl.bpProject.common.data.sessionHandler.Session

internal actual class FirebaseStorageInfrastructure actual constructor(
    private val session: Session,
) : FirebaseIntegration {
    override suspend fun uploadPlacesImages(
        images: List<Bitmap>
    ): List<ImageUploadResult> {
        TODO("Not yet implemented")
    }
}
