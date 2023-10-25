package williankl.bpProject.common.features.places.lisitng

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.data.networking.currentPage
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.networking.models.PagingState
import williankl.bpProject.common.data.networking.setLoading
import williankl.bpProject.common.data.networking.updateResult
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

    val placePagingResult = MutableStateFlow(PagingState<PlaceDisplayPresentation>())

    init {
        requestNextPage()
    }

    fun requestNextPage() = runAsync(
        onLoading = { placePagingResult.setLoading(true) },
        onContent = { placePagingResult.setLoading(false) },
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

        placePagingResult.updateResult {
            copy(
                currentPage = currentPage + 1,
                items = items + result,
                hasReachedFinalPage = result.isEmpty()
            )
        }
    }
}
