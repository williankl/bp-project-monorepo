package williankl.bpProject.common.data.imageRetrievalService

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.InputStream

public object ImageUriHandler {
    public fun retrieveImageFromUri(
        uri: Uri,
        contentResolver: ContentResolver,
        mutable: Boolean = true,
    ): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source) { imageDecoder, _, _ ->
                imageDecoder.isMutableRequired = mutable
            }
        } else {
            legacyImageRetrieval(uri, contentResolver)
        }
    }

    private fun legacyImageRetrieval(
        uri: Uri,
        contentResolver: ContentResolver
    ): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val imageStream = contentResolver.openInputStream(uri)
        return if (imageStream != null) {
            rotateImage(retrieveImageRotation(imageStream), bitmap)
        } else {
            bitmap
        }
    }

    private fun retrieveImageRotation(imageStream: InputStream): Float {
        val exif = ExifInterface(imageStream)

        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED,
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }
    }

    private fun rotateImage(
        rotation: Float,
        image: Bitmap,
    ): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotation)
        return Bitmap.createBitmap(
            image, 0, 0, image.width, image.height,
            matrix, true
        )
    }
}
