package williankl.bpProject.common.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap

@Composable
public actual fun imageFromUri(
    uri: Uri,
): Bitmap {
    TODO("To be implemented")
}

public actual fun Bitmap.toImageBitmap(): ImageBitmap {
    TODO("To be implemented")
}