package williankl.bpProject.common.data.firebaseIntegration.internal

import com.benasher44.uuid.Uuid
import korlibs.image.bitmap.Bitmap
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration

internal actual class FirebaseStorageInfrastructure : FirebaseIntegration {
    override suspend fun uploadPlacesImages(
        images: List<Bitmap>
    ): List<String> {
        TODO("Not yet implemented")
    }

}