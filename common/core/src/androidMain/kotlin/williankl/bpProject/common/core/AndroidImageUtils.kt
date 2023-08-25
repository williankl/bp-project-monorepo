package williankl.bpProject.common.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.chrynan.uri.core.Uri
import android.net.Uri as AndroidUri
import korlibs.image.bitmap.Bitmap
import korlibs.image.format.AndroidNativeImage
import korlibs.image.format.toAndroidBitmap

@Composable
public actual fun imageFromUri(
    uri: Uri,
): Bitmap {
    val context = LocalContext.current
    val bitmap = ImageUriHandler.retrieveImageFromUri(
        uri = AndroidUri.parse(uri.uriString),
        contentResolver = context.contentResolver
    )
    return AndroidNativeImage(bitmap)
}

public actual fun Bitmap.toImageBitmap(): ImageBitmap {
    return toAndroidBitmap().asImageBitmap()
}