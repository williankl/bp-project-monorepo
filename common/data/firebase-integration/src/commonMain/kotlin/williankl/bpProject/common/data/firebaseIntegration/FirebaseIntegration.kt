package williankl.bpProject.common.data.firebaseIntegration

import korlibs.image.bitmap.Bitmap
import williankl.bpProject.common.data.firebaseIntegration.models.ImageUploadResult

public interface FirebaseIntegration {
    public suspend fun uploadPlacesImages(
        images: List<Bitmap>,
    ): List<ImageUploadResult>
}
