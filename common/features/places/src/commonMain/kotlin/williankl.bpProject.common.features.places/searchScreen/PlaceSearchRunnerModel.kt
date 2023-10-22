package williankl.bpProject.common.features.places.searchScreen

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import kotlin.time.Duration.Companion.seconds

internal class PlaceSearchRunnerModel(
    private val mapsService: MapsService,
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
        val selectedAddress: MapPlaceResult? = null,
        val queryResults: List<MapPlaceResult> = emptyList()
    )

    fun queryFor(query: String) = setContent(
        onLoading = { /* Nothing */ },
    ) {
        val result = mapsService.queryForPlace(query)
        currentData.copy(
            queryResults = result
        )
    }

    fun updateFromCoordinate(coordinate: MapCoordinate) = setContent(
        onLoading = { /* Nothing */ },
    ) {
        currentData.copy(
            selectedAddress = mapsService.placeFromCoordinates(coordinate)
                .firstOrNull()
        )
    }

    fun updateAddressValue(place: MapPlaceResult?) = setContent(
        onLoading = { /* Nothing */ },
    ) {
        currentData.copy(
            selectedAddress = place
        )
    }
}
