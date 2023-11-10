package williankl.bpProject.common.features.places.creating.searchScreen

import dev.icerock.moko.maps.google.GoogleMapController
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.features.places.creating.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import williankl.bpProject.common.platform.design.components.maps.toMapCoordinate

internal class PlaceSearchRunnerModel(
    val mapController: GoogleMapController,
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

    fun initializeMap(
        initialCoordinate: MapCoordinate?,
    ) = runAsync(
        onLoading = { /* Nothing */ },
    ) {
        if (initialCoordinate != null) {
            withContext(Dispatchers.Main) {
                mapController.showLocation(
                    latLng = initialCoordinate.toMapCoordinate(),
                    zoom = 15f
                )
            }
        }

        mapController.onCameraScrollStateChanged = { isScrolling, isGesturing ->
            if (isScrolling.not() && isGesturing.not()) {
                updateFromCoordinate()
            }
        }
    }

    fun queryFor(query: String) = setContent(
        onLoading = { /* Nothing */ },
    ) {
        val result = mapsService.queryForPlace(query)
        currentData.copy(
            queryResults = result
        )
    }


    fun updateAddressValue(place: MapPlaceResult?) = setContent(
        onLoading = { /* Nothing */ },
    ) {
        currentData.copy(
            selectedAddress = place
        )
    }


    private fun updateFromCoordinate() = setContent(
        onLoading = { /* Nothing */ },
    ) {
        val coordinate = withContext(Dispatchers.Main) {
            mapController.getMapCenterLatLng().toMapCoordinate()
        }

        currentData.copy(
            selectedAddress = mapsService.placeFromCoordinates(coordinate)
                .firstOrNull()
        )
    }
}
