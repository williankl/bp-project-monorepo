package williankl.bpProject.common.data.imageRetrievalService.retriever

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import android.net.Uri as AndroidUri
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.AndroidNativeImage
import korlibs.image.format.toAndroidBitmap
import williankl.bpProject.common.data.imageRetrievalService.ImageUriHandler

public actual fun Bitmap.toImageBitmap(): ImageBitmap {
    return toAndroidBitmap().asImageBitmap()
}

public fun imageFromUri(
    context: Context,
    uri: Uri,
): Bitmap {
    val bitmap = ImageUriHandler.retrieveImageFromUri(
        uri = AndroidUri.parse(uri.uriString),
        contentResolver = context.contentResolver
    )
    return AndroidNativeImage(bitmap)
}

public fun AndroidUri.toMultiplatformUri(): Uri {
    return Uri.fromString(toString())
}

public fun Uri.toAndroidUri(): AndroidUri {
    return AndroidUri.parse(uriString)
}