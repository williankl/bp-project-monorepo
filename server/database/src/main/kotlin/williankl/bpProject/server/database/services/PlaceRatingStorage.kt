package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest

public interface PlaceRatingStorage {
    public suspend fun createRating(
        ownerId: Uuid,
        placeId: Uuid,
        data: PlaceRatingRequest,
    )

    public suspend fun ratingsForPlace(
        placeId: Uuid,
        page: Int,
        limit: Int,
    ): List<PlaceRating>

    public suspend fun deleteRating(id: Uuid)

    public suspend fun retrieveRating(id: Uuid): PlaceRating?
}
