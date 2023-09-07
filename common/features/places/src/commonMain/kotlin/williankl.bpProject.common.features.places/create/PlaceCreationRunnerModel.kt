package williankl.bpProject.common.features.places.create

import androidx.compose.ui.graphics.ImageBitmap
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.data.imageRetrievalService.retriever.toImageBitmap
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.create.PlaceCreationRunnerModel.PlaceCreationPresentation
import williankl.bpProject.common.features.places.create.handler.CreationHandler
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceCreationRunnerModel(
    private val imageRetriever: ImageRetriever,
    dispatcher: CoroutineDispatcher,
): RunnerModel<PlaceCreationPresentation>(
    dispatcher = dispatcher,
    initialData = PlaceCreationPresentation(),
) {

    internal val creationHandler by lazy {
        CreationHandler()
    }

    internal data class PlaceCreationPresentation(
        val suggestedPlaces: List<MapPlaceResult> = emptyList(),
        val images: List<ImageBitmap> = emptyList(),
    )

    fun retrievePresentation(uriList: List<String>) = setContent {
        val parsedUriList = uriList.map(Uri::fromString)

        PlaceCreationPresentation(
            images = parsedUriList
                .map(imageRetriever::retrieveImageFromUri)
                .map { bitmap -> bitmap.toImageBitmap() }
        )
    }
}