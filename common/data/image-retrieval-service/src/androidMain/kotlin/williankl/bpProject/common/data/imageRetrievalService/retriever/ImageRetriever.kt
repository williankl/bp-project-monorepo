package williankl.bpProject.common.data.imageRetrievalService.retriever

import android.content.Context
import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.AndroidNativeImage
import williankl.bpProject.common.data.imageRetrievalService.ImageUriHandler
import williankl.bpProject.common.data.imageRetrievalService.toAndroidUri

public actual class ImageRetriever(
    private val context: Context,
) {
    public actual fun retrieveImageFromUri(uri: Uri): Bitmap {
        val bitmap = ImageUriHandler.retrieveImageFromUri(
            uri = uri.toAndroidUri(),
            contentResolver = context.contentResolver
        )

        return AndroidNativeImage(bitmap)
    }
}
