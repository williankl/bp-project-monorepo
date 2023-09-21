package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceAddress
import java.util.UUID
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceDistanceQuery
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest

public interface PlaceRatingStorage {
    public suspend fun createComment(
        ownerId: Uuid,
        placeId: Uuid,
        data: PlaceRatingRequest,
    )

    public suspend fun commentsForPlace(
        id: Uuid,
        page: Int,
        limit: Int,
    ): List<PlaceRating>

    public suspend fun deleteComment(id: Uuid)
}
