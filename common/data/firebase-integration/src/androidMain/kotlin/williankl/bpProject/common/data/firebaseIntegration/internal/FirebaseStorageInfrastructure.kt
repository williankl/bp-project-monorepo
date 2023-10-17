package williankl.bpProject.common.data.firebaseIntegration.internal

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.toAndroidBitmap
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.firebaseIntegration.models.ImageUploadResult
import williankl.bpProject.common.data.imageRetrievalService.ImageTransformer
import williankl.bpProject.common.data.imageRetrievalService.ImageTransformer.EncodeQuality
import williankl.bpProject.common.data.sessionHandler.Session

internal actual class FirebaseStorageInfrastructure actual constructor(
    private val session: Session,
) : FirebaseIntegration {

    private val randomId: Uuid
        get() = uuid4()

    override suspend fun uploadPlacesImages(images: List<Bitmap>): List<ImageUploadResult> {
        val user = session.loggedInUser()
            ?: error("User not logged in")

        return images.map { image ->
            ImageUploadResult(
                originalImageUrl = uploadImage(user.id, image, EncodeQuality.Original),
                url = uploadImage(user.id, image, EncodeQuality.LowQuality),
            )
        }
    }

    private suspend fun uploadImage(
        userId: Uuid,
        bitmap: Bitmap,
        quality: EncodeQuality,
    ): String {
        val encodedImage = ImageTransformer.encodeImage(bitmap.toAndroidBitmap(), quality)
        val storageReference = retrieveStorageReference(userId)
        storageReference
            .putBytes(encodedImage)
            .retrieve()

        return storageReference.downloadUrl.retrieve()?.toString()
            ?: error("Could not retrieve file uri")
    }

    private fun retrieveStorageReference(userId: Uuid): StorageReference {
        return Firebase.storage.reference.child("$userId/$randomId.jpg")
    }
}
