package williankl.bpProject.common.features.places.lisitng

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.data.placeService.services.UserLocationService
import williankl.bpProject.common.features.places.components.PlaceDisplayPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceListingRunnerModel(
    private val placeQualifier: PlaceQualifier,
    private val placesService: PlacesService,
    private val mapsService: MapsService,
    private val userLocationService: UserLocationService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    dispatcher = dispatcher,
    initialData = Unit,
) {

    var isPagingLoading by mutableStateOf(false)
        private set
    var placePagingResult by mutableStateOf(PagingResult<PlaceDisplayPresentation>())
        private set

    init {
        requestNextPage()
    }

    fun requestNextPage() = runAsync(
        onLoading = { isPagingLoading = true },
        onContent = { isPagingLoading = false },
    ) {
        val places = placesService.retrievePlaces(
            qualifier = placeQualifier,
            page = placePagingResult.currentPage,
            limit = 20,
        )

        val distances = userLocationService.lastUserCoordinates()
            ?.let { userCoordinate ->
                mapsService.distanceBetween(
                    from = userCoordinate,
                    *places.map { place -> place.address.coordinates }.toTypedArray()
                )
            }

        val result = places.mapIndexed { index, place ->
            PlaceDisplayPresentation(
                place = place,
                distanceLabel = distances?.getOrNull(index)
            )
        }

        placePagingResult = with(placePagingResult) {
            copy(
                currentPage = currentPage + 1,
                items = items + result,
                hasReachedFinalPage = result.isEmpty()
            )
        }
    }
}
