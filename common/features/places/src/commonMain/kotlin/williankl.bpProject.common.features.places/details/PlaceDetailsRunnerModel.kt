package williankl.bpProject.common.features.places.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.benasher44.uuid.Uuid
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.data.networking.models.PagingResult
import williankl.bpProject.common.data.placeService.PlaceRatingService
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

    init {
        fetchNextCommentPage()
        updatePresentation()
    }

    var ratingPaging by mutableStateOf<PagingResult<PlaceRating>>(PagingResult())
        private set

    internal data class PlaceDetailsPresentation(
        val currentUser: User? = null,
        val averageColorList: List<Color> = emptyList(),
    )

    fun updatePresentation() = setContent {
        PlaceDetailsPresentation(
            currentUser = session.loggedInUser()
        )
    }

    fun fetchNextCommentPage() = runAsync {
        if (ratingPaging.hasReachedFinalPage) return@runAsync
        ratingPaging = with(ratingPaging) {
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
