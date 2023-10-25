package williankl.bpProject.common.features.dashboard.pages.home

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.data.placeService.services.UserLocationService
import williankl.bpProject.common.features.dashboard.pages.home.HomeRunnerModel.HomePresentation
import williankl.bpProject.common.features.places.components.PlaceDisplayPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class HomeRunnerModel(
    private val placeService: PlacesService,
    private val mapsService: MapsService,
    private val userLocationService: UserLocationService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<HomePresentation>(
    dispatcher = dispatcher,
    initialData = HomePresentation()
) {

    internal data class HomePresentation(
        val nearestPlaces: List<PlaceDisplayPresentation> = emptyList(),
        val favouritePlaces: List<Place> = emptyList(),
    )

    init {
        setContent { refreshPresentation() }
    }

    private suspend fun refreshPresentation(): HomePresentation {
        return coroutineScope {
            val nearestPlacesDeferred = async {
                placeService.retrievePlaces(
                    page = 0,
                    limit = 4,
                )
            }

            val defaultPlacesDeferred = async {
                placeService.retrievePlaces(
                    page = 0,
                    limit = 20,
                )
            }

            HomePresentation(
                nearestPlaces = mapToPresentation(nearestPlacesDeferred.await()),
                favouritePlaces = defaultPlacesDeferred.await(),
            )
        }
    }

    private suspend fun mapToPresentation(places: List<Place>): List<PlaceDisplayPresentation> {
        val lastUserCoordinates = userLocationService.lastUserCoordinates()
        val placesCoordinates = places.map { place -> place.address.coordinates }

        val distanceList = lastUserCoordinates
            ?.let { from ->
                mapsService.distanceBetween(
                    from = from,
                    to = placesCoordinates.toTypedArray(),
                )
            }.orEmpty()

        return places.mapIndexed { index, place ->
            PlaceDisplayPresentation(
                place = place,
                distanceLabel = distanceList.getOrNull(index),
            )
        }
    }
}
