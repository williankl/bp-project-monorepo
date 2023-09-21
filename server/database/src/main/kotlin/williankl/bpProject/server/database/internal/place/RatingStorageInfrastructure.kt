package williankl.bpProject.server.database.internal.place

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.server.database.internal.DriverProvider
import williankl.bpProject.server.database.internal.place.Mapper.toDomain
import williankl.bpProject.server.database.services.PlaceRatingStorage

internal class RatingStorageInfrastructure(
    private val driver: JdbcDriver,
) : PlaceRatingStorage {
    override suspend fun createComment(
        ownerId: Uuid,
        placeId: Uuid,
        data: PlaceRatingRequest
    ) {
        DriverProvider.withDatabase(driver) {
            placeRatingQueries.create(
                toDomain(ownerId, placeId, data)
            )
        }
    }

    override suspend fun commentsForPlace(
        id: Uuid,
        page: Int,
        limit: Int,
    ): List<PlaceRating> {
        return DriverProvider.withDatabase(driver) {
            placeRatingQueries.listPlacesComments(
                id,
                page.toLong(),
                (page * limit).toLong()
            )
                .executeAsList()
                .map(::toDomain)
        }
    }

    override suspend fun deleteComment(id: Uuid) {
        DriverProvider.withDatabase(driver) {
            placeRatingQueries.deletePlaceComment(id)
        }
    }
}