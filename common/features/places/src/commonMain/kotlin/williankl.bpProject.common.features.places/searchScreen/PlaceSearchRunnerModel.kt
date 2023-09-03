package williankl.bpProject.common.features.places.searchScreen

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.data.placeService.PlacesService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceSearchRunnerModel(
    private val placesService: PlacesService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceSearchPresentation>(
    dispatcher = dispatcher,
    initialData = PlaceSearchPresentation()
) {

    internal companion object{
        const val MINIMUM_SEARCH_LENGTH = 3
    }

    internal data class PlaceSearchPresentation(
        val queryResults: List<MapPlaceResult> = emptyList()
    )

    fun queryFor(query: String) = setContent(
        onLoading = { /* Nothing */ }
    ){
        val result = placesService.queryForPlace(query)
        currentData.value.copy(
            queryResults = result
        )
    }
}
