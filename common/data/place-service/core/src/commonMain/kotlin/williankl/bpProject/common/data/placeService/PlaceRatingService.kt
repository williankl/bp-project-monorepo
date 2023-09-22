package williankl.bpProject.common.data.placeService

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest

public interface PlaceRatingService {
    public suspend fun ratePlace(
        placeId: Uuid,
        rateRequest: PlaceRatingRequest
    )

    public suspend fun ratingsForPlace(
        placeId: Uuid,
        page: Int,
        limit: Int,
    ): List<PlaceRating>

    public suspend fun deleteRating(
        ratingId: Uuid,
    )
}
