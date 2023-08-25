package williankl.bpProject.common.features.places.photoSelection

import androidx.compose.ui.graphics.ImageBitmap
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.data.imageRetrievalService.retriever.toImageBitmap
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PhotoSelectionRunnerModel(
    private val imageRetriever: ImageRetriever,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PhotoSelectionPresentation>(
    dispatcher = dispatcher,
    initialData = PhotoSelectionPresentation()
) {

    internal data class PhotoSelectionPresentation(
        val images: List<ImageBitmap> = emptyList()
    )

    fun retrievePresentation(uriList: List<String>) = setContent {
        val parsedUriList = uriList.map(Uri::fromString)

        PhotoSelectionPresentation(
            images = parsedUriList
                .map(imageRetriever::retrieveImageFromUri)
                .map { bitmap -> bitmap.toImageBitmap() }
        )
    }
}
