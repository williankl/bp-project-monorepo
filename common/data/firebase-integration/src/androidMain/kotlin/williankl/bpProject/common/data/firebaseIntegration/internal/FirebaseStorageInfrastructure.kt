package williankl.bpProject.common.data.firebaseIntegration.internal

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.toAndroidBitmap
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.imageRetrievalService.ImageTransformer
import williankl.bpProject.common.data.placeService.internal.retrieve

internal actual class FirebaseStorageInfrastructure : FirebaseIntegration {

    private val randomId: Uuid
        get() = uuid4()

    override suspend fun uploadPlacesImages(images: List<Bitmap>): List<String> {
        val userId = uuid4() // fixme retrieve user id correctly
        return images.map { image ->
            uploadImage(userId, image)
        }
    }

    private suspend fun uploadImage(
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
