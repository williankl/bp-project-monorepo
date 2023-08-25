package williankl.bpProject.common.data.imageRetrievalService.retriever

import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap

public expect class ImageRetriever {

    public fun retrieveImageFromUri(uri: Uri): Bitmap
}
