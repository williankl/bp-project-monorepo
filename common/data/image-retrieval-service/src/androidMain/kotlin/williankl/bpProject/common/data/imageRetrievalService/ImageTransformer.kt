package williankl.bpProject.common.data.imageRetrievalService

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

public object ImageTransformer {

    public suspend fun encodeImage(
        image: Bitmap,
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    public suspend fun decodeImage(imageBytes: ByteArray): Bitmap {
        return decodeImage(imageBytes.toStoringString())
    }

    public suspend fun decodeImage(encodedImage: String): Bitmap {
        val encodeByte = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    public fun ByteArray.toStoringString(): String {
        return Base64.encodeToString(this, Base64.DEFAULT)
    }
}
