package williankl.bpProject.server.database.internal.place

import place.PlaceAddressData
import place.PlaceData
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.Place.PlaceAddress.PlaceAddressCoordinate
import williankl.bpProject.common.core.models.Season

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
                displayName = name,
                description = description,
                address = with(address) {
                    PlaceAddress(
                        id = id,
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
                seasons = seasons.split(IMAGE_SEPARATOR).sanitizeSeasonList(),
            )
        }
    }

    fun toPlaceData(from: Place): PlaceData {
        return with(from) {
            PlaceData(
                id = id,
                ownerId = ownerId,
                name = displayName,
                description = description,
                addressId = address.id,
                images = imageUrls.joinToString(IMAGE_SEPARATOR),
                seasons = seasons.joinToString(IMAGE_SEPARATOR),
            )
        }
    }

    fun toAddressData(from: PlaceAddress): PlaceAddressData {
        return with(from) {
            PlaceAddressData(
                id = id,
                city = city,
                country = country,
                street = street,
                latitude = coordinates.latitude.toString(),
                longitude = coordinates.longitude.toString(),
            )
        }
    }

    private fun List<String>.sanitizeSeasonList(): List<Season> {
        return mapNotNull { code ->
            Season.entries.firstOrNull { season -> season.name == code }
        }
    }
}
