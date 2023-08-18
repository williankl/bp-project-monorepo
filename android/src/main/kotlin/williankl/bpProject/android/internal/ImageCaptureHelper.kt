package williankl.bpProject.android.internal

import android.content.Context
import android.net.Uri
import java.io.File

internal object ImageCaptureHelper {

    private const val IMAGE_NAME: String = "capturing-cached-image.jpeg"

    fun cacheUri(context: Context): Uri? {
        return Uri.fromFile(cacheFile(context))
    }

    fun cacheFile(context: Context): File {
        return File(context.cacheDir, IMAGE_NAME)
    }
}
