package williankl.bpProject.common.data.imageRetrievalService

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

public object ImageTransformer {

    public enum class EncodeQuality(
        internal val factor: Int,
    ) {
        Original(100),
        LowQuality(30),
    }

    public suspend fun encodeImage(
        image: Bitmap,
        quality: EncodeQuality,
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, quality.factor, stream)
        return stream.toByteArray()
    }

    public suspend fun decodeImage(imageBytes: ByteArray): Bitmap {
        return decodeImage(imageBytes.toStoringString())
    }

    public suspend fun downSampleImage(
        bitmap: Bitmap,
        quality: EncodeQuality,
        filtering: Boolean = true,
    ): Bitmap {
        val percentageFactor = quality.factor * 0.01f
        return Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * percentageFactor).roundToInt(),
            (bitmap.height * percentageFactor).roundToInt(),
            filtering,
        )
    }

    public suspend fun decodeImage(encodedImage: String): Bitmap {
        val encodeByte = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    public fun ByteArray.toStoringString(): String {
        return Base64.encodeToString(this, Base64.DEFAULT)
    }
}
