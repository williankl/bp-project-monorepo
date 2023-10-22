package williankl.bpProject.common.data.placeService.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.data.placeService.models.PlaceRatingData

public interface PlaceRatingService {
    public suspend fun ratePlace(
        placeId: Uuid,
        rateRequest: PlaceRatingRequest
    )

    public suspend fun placeRatingData(placeId: Uuid): PlaceRatingData

    public suspend fun ratingsForPlace(
        placeId: Uuid,
        page: Int,
        limit: Int = 10,
    ): List<PlaceRating>

    public suspend fun deleteRating(
        ratingId: Uuid,
    )
}
