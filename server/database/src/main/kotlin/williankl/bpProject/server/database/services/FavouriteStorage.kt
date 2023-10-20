package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place

public interface FavouriteStorage {

    public suspend fun userFavourites(
        userId: Uuid,
        page: Int,
        limit: Int,
    ): List<Place>

    public suspend fun isFavourite(
        userId: Uuid,
        placeId: Uuid,
    ): Boolean

    public suspend fun setFavouriteTo(
        userId: Uuid,
        placeId: Uuid,
        to: Boolean,
    )
}
