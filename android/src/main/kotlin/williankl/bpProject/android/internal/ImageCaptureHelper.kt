package williankl.bpProject.android.internal

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

internal object ImageCaptureHelper {

    private const val PROVIDER_NAME: String = ".cache"
    private const val IMAGE_NAME: String = "capturing-cached-image.jpeg"

    fun cacheUri(context: Context): Uri? {
        return FileProvider.getUriForFile(context, context.packageName + PROVIDER_NAME, cacheFile(context))
    }

    fun cacheFile(context: Context): File {
        return File(context.cacheDir, IMAGE_NAME)
    }
}
