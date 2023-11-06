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
    override suspend fun createRating(
        ownerId: Uuid,
        placeId: Uuid,
        data: PlaceRatingRequest
    ): PlaceRating {
        val generatedPlace = toDomain(ownerId, placeId, data)

        DriverProvider.withDatabase(driver) {
            placeRatingQueries.create(generatedPlace)
        }

        return retrieveRating(generatedPlace.id)
            ?: error("Could not retrieve created place")
    }

    override suspend fun ratingsForPlace(
        placeId: Uuid,
        page: Int,
        limit: Int,
    ): List<PlaceRating> {
        return DriverProvider.withDatabase(driver) {
            placeRatingQueries.listPlacesRatings(
                placeId,
                limit.toLong(),
                (limit * page).toLong()
            )
                .executeAsList()
                .map(::toDomain)
        }
    }

    override suspend fun deleteRating(id: Uuid) {
        DriverProvider.withDatabase(driver) {
            placeRatingQueries.deletePlaceRating(id)
        }
    }

    override suspend fun retrieveRating(id: Uuid): PlaceRating? {
        return DriverProvider.withDatabase(driver) {
            placeRatingQueries.retrieveRating(id)
                .executeAsOneOrNull()
                ?.let(::toDomain)
        }
    }
}
