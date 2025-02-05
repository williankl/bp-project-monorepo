package williankl.bpProject.common.data.imageRetrievalService

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.AndroidNativeImage
import korlibs.image.format.toAndroidBitmap
import android.net.Uri as AndroidUri

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
