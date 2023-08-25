package williankl.bpProject.common.data.imageRetrievalService

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

public object ImageCaptureHelper {

    private const val PROVIDER_NAME: String = ".cache"
    private const val IMAGE_NAME: String = "capturing-cached-image.jpeg"

    public fun cacheUri(context: Context): Uri? {
        return FileProvider.getUriForFile(context, context.packageName + PROVIDER_NAME, cacheFile(context))
    }

    public fun cacheFile(context: Context): File {
        return File(context.cacheDir, IMAGE_NAME)
    }
}
