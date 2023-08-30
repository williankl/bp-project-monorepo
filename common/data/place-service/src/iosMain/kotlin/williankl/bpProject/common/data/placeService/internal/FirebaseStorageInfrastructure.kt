package williankl.bpProject.common.data.placeService.internal

import com.benasher44.uuid.Uuid
import korlibs.image.bitmap.Bitmap

internal actual class FirebaseStorageInfrastructure {

    actual suspend fun uploadImage(
        userId: Uuid,
        bitmap: Bitmap
    ): String {
        TODO("Still needs to be implemented")
    }
}