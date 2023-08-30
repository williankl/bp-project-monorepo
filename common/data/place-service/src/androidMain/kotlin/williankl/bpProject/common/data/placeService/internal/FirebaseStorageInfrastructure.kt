package williankl.bpProject.common.data.placeService.internal

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.toAndroidBitmap
import williankl.bpProject.common.data.imageRetrievalService.ImageTransformer

internal actual class FirebaseStorageInfrastructure {

    private val randomId: Uuid
        get() = uuid4()

    actual suspend fun uploadImage(
        userId: Uuid,
        bitmap: Bitmap
    ): String {
        val encodedImage = ImageTransformer.encodeImage(bitmap.toAndroidBitmap())
        val result = retrieveStorageReference(userId)
            .putBytes(encodedImage)
            .retrieve()

        return result.uploadSessionUri?.toString()
            ?: error("Could not retrieve file uri")
    }

    private fun retrieveStorageReference(userId: Uuid): StorageReference {
        return Firebase.storage.reference.child("$userId/$randomId.jpg")
    }
}