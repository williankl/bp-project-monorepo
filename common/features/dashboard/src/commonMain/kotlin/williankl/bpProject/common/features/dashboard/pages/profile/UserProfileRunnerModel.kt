package williankl.bpProject.common.features.dashboard.pages.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.data.placeService.services.UserLocationService
import williankl.bpProject.common.data.sessionHandler.Session
import williankl.bpProject.common.features.dashboard.pages.profile.UserProfileRunnerModel.UserProfilePresentation
import williankl.bpProject.common.features.places.components.PlaceDisplayPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class UserProfileRunnerModel(
    private val session: Session,
    private val placesService: PlacesService,
    private val userLocationService: UserLocationService,
    private val mapsService: MapsService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<UserProfilePresentation>(
    dispatcher = dispatcher,
    initialData = UserProfilePresentation()
) {
    private var currentPage by mutableStateOf(0)
    var hasLoadedAllPages by mutableStateOf(false)
        private set

    internal data class UserProfilePresentation(
        val avatarUrl: String = "",
        val userFullName: String = "",
        val userTag: String? = null,
        val places: List<PlaceDisplayPresentation> = emptyList()
    )

    init {
        setContent { updatedUserData() }
    }

    fun loadNextPage() = runAsync(
        onLoading = { /* Nothing */ },
    ) {
        fetchNextPlacePage()
    }

    private suspend fun updatedUserData(): UserProfilePresentation {
        val user = session.loggedInUser()
            ?: error("User not logged in")

        return currentData.copy(
            avatarUrl = user.avatarUrl.orEmpty(),
            userFullName = user.name,
            userTag = user.tag,
        )
    }

    private suspend fun fetchNextPlacePage(
        refresh: Boolean = false,
    ) {
        if (refresh) {
            currentPage = 0
            hasLoadedAllPages = false
        }

        if (hasLoadedAllPages) return

        val currentUser = session.loggedInUser()
            ?: error("User not logged in")

        val foundPlaces = mapToPresentation(
            placesService.retrievePlaces(
                page = currentPage,
                fromUser = currentUser.id,
            )
        )

        currentPage++
        hasLoadedAllPages = foundPlaces.isEmpty()

        updateData { data ->
            data.copy(
                places = data.places + foundPlaces
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
