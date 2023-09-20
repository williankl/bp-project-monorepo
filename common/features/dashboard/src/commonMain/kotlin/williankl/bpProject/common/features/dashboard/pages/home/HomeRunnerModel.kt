package williankl.bpProject.common.features.dashboard.pages.home

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.PlacesService
import williankl.bpProject.common.features.dashboard.pages.home.HomeRunnerModel.HomePresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class HomeRunnerModel(
    private val placeService: PlacesService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<HomePresentation>(
    dispatcher = dispatcher,
    initialData = HomePresentation()
) {

    internal data class HomePresentation(
        val nearestPlaces: List<Place> = emptyList(),
        val favouritePlaces: List<Place> = emptyList(),
    )

    init {
        setContent { refreshPresentation() }
    }

    private suspend fun refreshPresentation(): HomePresentation {
        return HomePresentation(
            nearestPlaces = placeService.retrievePlaces(
                page = 0,
                limit = 4,
            ),
            favouritePlaces = placeService.retrievePlaces(
                page = 0,
                limit = 20,
            ),
        )
    }
}
