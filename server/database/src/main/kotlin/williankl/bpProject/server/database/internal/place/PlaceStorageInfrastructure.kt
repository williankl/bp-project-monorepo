package williankl.bpProject.server.database.internal.place

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.network.request.PlaceDistanceQuery
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.place.Mapper.toAddressData
import williankl.bpProject.server.database.internal.place.Mapper.toDomain
import williankl.bpProject.server.database.internal.place.Mapper.toPlaceData
import williankl.bpProject.server.database.services.PlaceStorage
import java.util.*

internal class PlaceStorageInfrastructure(
    private val driver: JdbcDriver,
) : PlaceStorage {

    override suspend fun savePlace(place: Place) {
        withDatabase(driver) {
            placeDataQueries.create(toPlaceData(place))
            placeAddressQueries.create(toAddressData(place.address))
        }
    }

    override suspend fun retrievePlaces(
        page: Int,
        limit: Int,
        ownerId: Uuid?,
        state: Place.PlaceState?,
        distance: PlaceDistanceQuery?,
    ): List<Place> {
        return withDatabase(driver) {
            placeDataQueries.listPlaces(
                ownerId = ownerId,
                stateTag = state?.name,
                lat = distance?.coordinates?.latitude,
                lon = distance?.coordinates?.longitude,
                padding = distance?.maxDistance,
                limit = limit.toLong(),
                offset = (limit * page).toLong()
            )
                .executeAsList()
                .map(::toDomain)
        }
    }

    override suspend fun retrievePlace(id: UUID): Place? {
        return withDatabase(driver) {
            placeDataQueries.findPlaceById(id)
                .executeAsOneOrNull()
                ?.let(::toDomain)
        }
    }

    override suspend fun updatePlaceData(
        id: UUID,
        name: String,
        description: String?
    ) {
        withDatabase(driver) {
            placeDataQueries.updatePlaceData(
                id = id,
                name = name,
                description = description
            )
        }
    }

    override suspend fun updatePlaceLocation(
        id: UUID,
        address: Place.PlaceAddress
    ) {
        withDatabase(driver) {
            with(address) {
                placeAddressQueries.updateAddress(
                    id = id,
                    street = street,
                    city = city,
                    country = country,
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                )
            }
        }
    }
}
