package williankl.bpProject.server.database.internal.place

import place.PlaceAddressData
import place.PlaceData
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.*
import williankl.bpProject.common.core.models.Season

internal object Mapper {

    private const val LIST_SEPARATOR = "|"

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
                        coordinates = PlaceAddress.PlaceCoordinate(
                            latitude = latitude,
                            longitude = longitude,
                        )
                    )
                },
                imageUrls = images?.split(LIST_SEPARATOR).orEmpty(),
                seasons = seasons.split(LIST_SEPARATOR).sanitizeSeasonList(),
                tags = seasons.split(LIST_SEPARATOR).sanitizeTagList(),
                state = state.sanitizeState(),
                createdAt = createdAt,
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
                images = imageUrls.joinToString(LIST_SEPARATOR),
                seasons = seasons.joinToString(LIST_SEPARATOR),
                tags = tags.joinToString(LIST_SEPARATOR),
                createdAt = createdAt,
                state = state.name
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
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
            )
        }
    }

    private fun List<String>.sanitizeSeasonList(): List<Season> {
        return mapNotNull { code ->
            Season.entries.firstOrNull { season -> season.name == code }
        }
    }

    private fun List<String>.sanitizeTagList(): List<PlaceTag> {
        return mapNotNull { code ->
            PlaceTag.entries.firstOrNull { tag -> tag.name == code }
        }
    }

    private fun String.sanitizeState(): PlaceState {
        return PlaceState.entries
            .firstOrNull { entry -> entry.name == this }
            ?: error("$this not mapped as place state")
    }
}
