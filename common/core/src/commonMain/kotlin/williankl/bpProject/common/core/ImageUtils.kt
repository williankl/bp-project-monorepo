package williankl.bpProject.common.core

import androidx.compose.ui.graphics.ImageBitmap
import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap

public expect fun imageFromUri(uri: Uri): Bitmap

public expect fun Bitmap.toImageBitmap(): ImageBitmap