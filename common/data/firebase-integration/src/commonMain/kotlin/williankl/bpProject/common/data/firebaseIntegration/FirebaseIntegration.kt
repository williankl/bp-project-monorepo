package williankl.bpProject.common.data.firebaseIntegration

import korlibs.image.bitmap.Bitmap

public interface FirebaseIntegration {
    public suspend fun uploadPlacesImages(
        images: List<Bitmap>,
    ): List<String>
}
