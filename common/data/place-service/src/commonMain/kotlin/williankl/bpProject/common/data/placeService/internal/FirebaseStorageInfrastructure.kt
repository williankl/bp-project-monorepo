package williankl.bpProject.common.data.placeService.internal

import com.benasher44.uuid.Uuid
import korlibs.image.bitmap.Bitmap

internal expect class FirebaseStorageInfrastructure() {
    suspend fun uploadImage(
        userId: Uuid,
        bitmap: Bitmap,
    ): String
}