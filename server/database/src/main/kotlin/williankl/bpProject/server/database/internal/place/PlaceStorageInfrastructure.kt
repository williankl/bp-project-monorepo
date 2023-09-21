package williankl.bpProject.server.database.internal.place

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.place.Mapper.toAddressData
import williankl.bpProject.server.database.internal.place.Mapper.toDomain
import williankl.bpProject.server.database.internal.place.Mapper.toPlaceData
import williankl.bpProject.server.database.services.PlaceStorage
import java.util.*
import williankl.bpProject.common.core.models.network.request.PlaceDistanceQuery

internal class PlaceStorageInfrastructure(
    private val driver: JdbcDriver,
) : PlaceStorage {

    override suspend fun savePlace(place: Place) {
        withDatabase(driver) {
            placeDataQueries.createFullPlace(toPlaceData(place))
            placeAddressQueries.createFullAddress(toAddressData(place.address))
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
            val placeDataList = placeDataQueries.listPlaces(
                ownerId,
                state?.name,
                distance?.coordinates?.latitude,
                distance?.coordinates?.longitude,
                distance?.coordinatePadding,
                limit.toLong(),
                (limit * page).toLong()
            )

            emptyList()
        }
    }

    override suspend fun retrievePlace(id: UUID): Place? {
        return withDatabase(driver) {
            val placeData = placeDataQueries.findPlaceById(id)
                .executeAsOneOrNull()

            val addressData =
                placeData?.addressId
                    ?.let(placeAddressQueries::findAddressById)
                    ?.executeAsOneOrNull()

            if (placeData != null && addressData != null) {
                toDomain(placeData, addressData)
            } else null
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
