package williankl.bpProject.server.database.internal.place

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.inRangeOf
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
                limit.toLong(),
                (limit * page).toLong()
            )
                .executeAsList()
                .map(::toDomain)
                .filter { place ->
                    val filterByOwner = ownerId
                        ?.let { ownerId == place.owner.id }
                        ?: true

                    val filterByState = state
                        ?.let { state == place.state }
                        ?: true

                    val filterByDistance = distance
                        ?.let {
                            place.address.coordinates.inRangeOf(
                                other = distance.coordinates,
                                padding = distance.maxDistance,
                            )
                        } ?: true

                    filterByOwner && filterByState && filterByDistance
                }
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
