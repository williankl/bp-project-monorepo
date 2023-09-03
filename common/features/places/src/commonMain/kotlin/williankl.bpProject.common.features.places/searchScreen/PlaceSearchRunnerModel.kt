package williankl.bpProject.common.features.places.searchScreen

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.placeService.PlacesService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import kotlin.time.Duration.Companion.seconds

internal class PlaceSearchRunnerModel(
    private val placesService: PlacesService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceSearchPresentation>(
    dispatcher = dispatcher,
    initialData = PlaceSearchPresentation()
) {

    internal companion object {
        const val MINIMUM_SEARCH_LENGTH = 3
        val queryDebounce = 0.5.seconds
    }

    internal data class PlaceSearchPresentation(
        val queryResults: List<MapPlaceResult> = emptyList()
    )

    fun queryFor(query: String) = setContent(
        onLoading = { /* Nothing */ }
    ) {
        val result = placesService.queryForPlace(query)
        currentData.value.copy(
            queryResults = result
        )
    }
}
