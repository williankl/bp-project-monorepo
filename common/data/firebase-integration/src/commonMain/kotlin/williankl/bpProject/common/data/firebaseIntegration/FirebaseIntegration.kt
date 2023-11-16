package williankl.bpProject.common.data.firebaseIntegration

import dev.icerock.moko.media.Bitmap
import williankl.bpProject.common.data.firebaseIntegration.models.ImageUploadResult

public interface FirebaseIntegration {
    public suspend fun uploadPlacesImages(
        images: List<Bitmap>,
    ): List<ImageUploadResult>
}
