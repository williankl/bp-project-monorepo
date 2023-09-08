package williankl.bpProject.common.features.places.photoSelection

import androidx.compose.ui.graphics.Color
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import korlibs.image.bitmap.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.averageColor
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
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

    internal data class PhotoSelectionPresentation(
        val imageAverageColor: Color = BeautifulColor.Secondary.nonComposableColor(),
        val imageMap: Map<Uri, Bitmap> = emptyMap(),
    )

    fun retrievePresentation(uriList: List<String>) = runAsync {
        val parsedUriList = uriList.map(Uri::fromString)
        updateData { currentData ->
            currentData.copy(
                imageMap = parsedUriList
                    .associateWith { uri ->
                        imageRetriever.retrieveImageFromUri(uri)
                    }
            )
        }
    }

    fun handleImageRemoval(uri: Uri) = runAsync {
        updateData { currentData ->
            currentData.copy(
                imageMap = currentData.imageMap.filter { (mapUri, _) ->
                    mapUri != uri
                }
            )
        }
    }

    fun updateImageAverageColor(image: Bitmap) = runAsync(
        onLoading = { /* Nothing by default */ }
    ) {
        val averageColor = image.averageColor()
        updateData { currentData ->
            currentData.copy(
                imageAverageColor = averageColor
            )
        }
    }
}
