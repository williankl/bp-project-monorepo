package williankl.bpProject.common.features.places.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.benasher44.uuid.Uuid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.placeService.PlaceRatingService
import williankl.bpProject.common.data.placeService.models.PlaceRatingData
import williankl.bpProject.common.data.sessionHandler.Session
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.nonComposableColor
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceDetailsRunnerModel(
    private val placeId: Uuid,
    private val session: Session,
    private val ratingService: PlaceRatingService,
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
        val placeRatingData: PlaceRatingData? = null,
        val averageColorList: List<Color> = emptyList(),
    )

    fun updatePresentation() = setContent {
        currentData.value.copy(
            currentUser = session.loggedInUser(),
            placeRatingData = ratingService.placeRatingData(placeId)
        )
    }

    fun fetchNextCommentPage(resetting: Boolean = false) = runAsync {
        mutableRatingPaging.update { paging ->
            if(resetting) return@update PagingResult<PlaceRating>()
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
    }
}
