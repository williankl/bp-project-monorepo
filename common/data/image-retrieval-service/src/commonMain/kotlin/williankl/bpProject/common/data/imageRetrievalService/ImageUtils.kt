package williankl.bpProject.common.data.imageRetrievalService

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import dev.icerock.moko.media.Bitmap
import dev.icerock.moko.media.compose.toImageBitmap

public suspend fun Bitmap.averageColor(): Color {
    val imageBitmap = toImageBitmap()
    var redBucket = 0f
    var greenBucket = 0f
    var blueBucket = 0f
    val pixelCount = imageBitmap.width * imageBitmap.height

    val pixelBuffer = IntArray(pixelCount)
    toImageBitmap().readPixels(pixelBuffer)

    pixelBuffer.forEach { intColor ->
        val color = Color(intColor)
        redBucket += color.red
        greenBucket += color.green
        blueBucket += color.blue
    }

    return Color(
        red = redBucket / pixelCount,
        green = greenBucket / pixelCount,
        blue = blueBucket / pixelCount,
    )
}
