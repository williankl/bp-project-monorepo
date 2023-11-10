package williankl.bpProject.common.features.places.creating.photoSelection

import androidx.compose.ui.graphics.Color
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import korlibs.image.bitmap.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.averageColor
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.features.places.creating.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.nonComposableColor
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PhotoSelectionRunnerModel(
    private val imageRetriever: ImageRetriever,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PhotoSelectionPresentation>(
    dispatcher = dispatcher,
    initialData = PhotoSelectionPresentation()
) {

    internal companion object {
        val defaultImageColor = BeautifulColor.Secondary.nonComposableColor()
    }

    internal data class PhotoSelectionPresentation(
        val imageDataList: List<SelectedImageData> = emptyList()
    ) {
        internal data class SelectedImageData(
            val uri: Uri,
            val image: Bitmap,
            val averageColor: Color,
        )
    }

    fun retrievePresentation(uriList: List<String>) = setContent {
        val parsedUriList = uriList.map { uriString ->
            val uri = Uri.fromString(uriString)
            val hardwareImage = imageRetriever.retrieveImageFromUri(uri)
            val image = imageRetriever.retrieveImageFromUri(
                uri = uri,
                allowHardware = false,
            )

            PhotoSelectionPresentation.SelectedImageData(
                uri = uri,
                image = hardwareImage,
                averageColor = image.averageColor(),
            )
        }

        PhotoSelectionPresentation(
            imageDataList = parsedUriList,
        )
    }

    fun handleImageRemoval(uri: Uri) = runAsync {
        updateData { currentData ->
            currentData.copy(
                imageDataList = currentData.imageDataList.filter { data ->
                    data.uri != uri
                }
            )
        }
    }
}
