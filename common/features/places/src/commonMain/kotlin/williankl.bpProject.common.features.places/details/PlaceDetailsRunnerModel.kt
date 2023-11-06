package williankl.bpProject.common.features.places.details

import androidx.compose.ui.graphics.Color
import com.benasher44.uuid.Uuid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.placeService.GoogleUriBuilder
import williankl.bpProject.common.data.placeService.models.PlaceRatingData
import williankl.bpProject.common.data.placeService.services.PlaceRatingService
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.data.placeService.services.UserLocationService
import williankl.bpProject.common.data.sessionHandler.Session
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.nonComposableColor
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import williankl.bpProject.common.platform.uriNavigator.UriNavigator

internal class PlaceDetailsRunnerModel(
    private val placeId: Uuid,
    private val session: Session,
    private val placesService: PlacesService,
    private val ratingService: PlaceRatingService,
    private val uriNavigator: UriNavigator,
    private val userLocationService: UserLocationService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceDetailsPresentation>(
    initialData = PlaceDetailsPresentation(),
    dispatcher = dispatcher,
) {
    internal companion object {
        val defaultImageColor = BeautifulColor.Secondary.nonComposableColor()
    }

    private val mutableRatingPaging = MutableStateFlow(PagingResult<PlaceRating>())
    val ratingPaging: StateFlow<PagingResult<PlaceRating>> = mutableRatingPaging

    init {
        fetchNextCommentPage()
        updatePresentation()
    }

    internal data class PlaceDetailsPresentation(
        val currentUser: User? = null,
        val place: Place? = null,
        val placeRatingData: PlaceRatingData? = null,
        val averageColorList: List<Color> = emptyList(),
        val isPlaceFavourite: Boolean = false,
    )

    fun updatePresentation() = setContent {
        val user = session.loggedInUser()
        currentData.copy(
            place = placesService.retrievePlace(placeId),
            currentUser = user,
            placeRatingData = ratingService.placeRatingData(placeId),
            isPlaceFavourite = if (user != null) {
                placesService.isPlaceFavourite(placeId)
            } else false,
        )
    }

    fun setToFavourite(settingTo: Boolean) = setContent(
        onLoading = { /* Nothing to load */ }
    ) {
        placesService.toggleFavouriteTo(placeId, settingTo)
        currentData.copy(
            isPlaceFavourite = settingTo
        )
    }

    fun fetchNextCommentPage(refreshing: Boolean = false) = runAsync(
        onLoading = { /* todo - show comment only loading */ },
        onContent = { /* todo - hide comment only loading */ }
    ) {
        mutableRatingPaging.update { paging ->
            if (refreshing) return@update PagingResult<PlaceRating>()
            if (paging.hasReachedFinalPage) return@runAsync

            with(paging) {
                val nextPage = ratingService.ratingsForPlace(
                    placeId = placeId,
                    page = currentPage + 1
                )

                copy(
                    currentPage = currentPage + 1,
                    hasReachedFinalPage = nextPage.isEmpty(),
                    items = items + nextPage,
                )
            }
        }
    }

    fun ratePlace(
        rating: Int,
        comment: String?,
    ) = runAsync {
        ratingService.ratePlace(
            placeId = placeId,
            rateRequest = PlaceRatingRequest(
                comment = comment,
                rating = rating,
            )
        )

        fetchNextCommentPage(refreshing = true)
    }

    fun openPlaceOnGoogleMaps(place: Place) = runAsync(
        onLoading = { /* Nothing */ }
    ) {
        uriNavigator.redirectFromString(
            GoogleUriBuilder.buildRouteUri(
                userLocationService.lastUserCoordinates(),
                place.address.coordinates
            )
        )
    }
}
