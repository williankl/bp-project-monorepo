package williankl.bpProject.server.database.internal.place

import place.PlaceAddressData
import place.PlaceData
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.Place.PlaceAddress.PlaceAddressCoordinate
import williankl.bpProject.common.core.models.Place.PlaceSeason
import java.util.*

internal object Mapper {

    private const val IMAGE_SEPARATOR = "|"

    fun toDomain(
        from: PlaceData,
        address: PlaceAddressData,
    ): Place {
        return with(from) {
            Place(
                id = id,
                ownerId = ownerId,
                name = name,
                description = description,
                address = with(address) {
                    PlaceAddress(
                        id = id.toString(),
                        street = street,
                        city = city,
                        country = country,
                        coordinates = PlaceAddressCoordinate(
                            latitude = latitude.toDouble(),
                            longitude = longitude.toDouble(),
                        )
                    )
                },
                imageUrls = images?.split(IMAGE_SEPARATOR).orEmpty(),
                season = PlaceSeason.valueOf(season),
            )
        }
    }

    fun toPlaceData(from: Place): PlaceData {
        return with(from) {
            PlaceData(
                id = id,
                ownerId = ownerId,
                name = name,
                description = description,
                addressId = UUID.fromString(address.id),
                images = imageUrls.joinToString(IMAGE_SEPARATOR),
                season = season.name,
            )
        }
    }

    fun toAddressData(from: PlaceAddress): PlaceAddressData {
        return with(from) {
            PlaceAddressData(
                id = UUID.fromString(id),
                city = city,
                country = country,
                street = street,
                latitude = coordinates.latitude.toString(),
                longitude = coordinates.longitude.toString(),
            )
        }
    }
}
