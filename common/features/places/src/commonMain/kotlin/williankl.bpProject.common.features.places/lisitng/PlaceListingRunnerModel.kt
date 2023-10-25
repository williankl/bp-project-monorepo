package williankl.bpProject.common.features.places.lisitng

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceListingRunnerModel(
    private val placeQualifier: PlaceQualifier,
    private val placesService: PlacesService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    dispatcher = dispatcher,
    initialData = Unit,
) {

    var placePagingResult by mutableStateOf(PagingResult<Place>())
        private set

    fun requestNextPage() = runAsync(
        onLoading = { /* Nothing */ },
    ) {
        val result = placesService.retrievePlaces(
            qualifier = placeQualifier,
            page = placePagingResult.currentPage,
            limit = 20,
        )

        placePagingResult = with(placePagingResult) {
            copy(
                currentPage = currentPage + 1,
                items = items + result,
                hasReachedFinalPage = result.isEmpty()
            )
        }
    }
}
