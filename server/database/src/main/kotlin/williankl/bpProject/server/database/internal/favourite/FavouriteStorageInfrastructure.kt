package williankl.bpProject.server.database.internal.favourite

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import favourite.FavouriteData
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.favourite.Mapper.toDomain
import williankl.bpProject.server.database.services.FavouriteStorage
import williankl.bpProject.server.database.services.ImageStorage

internal class FavouriteStorageInfrastructure(
    private val driver: JdbcDriver,
    private val imageStorage: ImageStorage,
) : FavouriteStorage {
    override suspend fun userFavourites(
        userId: Uuid,
        page: Int,
        limit: Int,
    ): List<Place> {
        return withDatabase(driver) {
            favouriteDataQueries.retrieveFavouritePlaces(
                userId = userId,
                limit = limit.toLong(),
                offset = (limit * page).toLong()
            )
                .executeAsList()
                .map { joinedData ->
                    toDomain(
                        joinedData = joinedData,
                        images = imageStorage.imagesFromPlace(joinedData.placeId)
                    )
                }
        }
    }

    override suspend fun isFavourite(
        userId: Uuid,
        placeId: Uuid,
    ): Boolean {
        return withDatabase(driver) {
            favouriteDataQueries.isPlaceFavourite(
                userId = userId,
                placeId = placeId,
            )
                .executeAsList()
                .isNotEmpty()
        }
    }

    override suspend fun setFavouriteTo(
        userId: Uuid,
        placeId: Uuid,
        to: Boolean,
    ) {
        withDatabase(driver) {
            if (to) {
                favouriteDataQueries.create(FavouriteData(placeId, userId))
            } else {
                favouriteDataQueries.dropFavouritePlace(placeId, userId)
            }
        }
    }
}
