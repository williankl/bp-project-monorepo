package williankl.bpProject.common.data.imageRetrievalService.retriever

import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap

public actual class ImageRetriever {

    public actual fun retrieveImageFromUri(
        uri: Uri,
        allowHardware: Boolean,
    ): Bitmap {
        TODO("To be implemented")
    }
}
