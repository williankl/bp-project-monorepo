package williankl.bpProject.server.database.internal.place

import com.benasher44.uuid.Uuid
import place.FindPlaceById
import place.ListPlaces
import place.ListPlacesRatings
import place.PlaceAddressData
import place.PlaceData
import place.RetrieveRating
import williankl.bpProject.common.core.generateId
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.*
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import java.util.Date
import place.PlaceRating as DBPlaceRating

internal object Mapper {

    private const val LIST_SEPARATOR = "|"

    fun toDomain(
        ownerId: Uuid,
        placeId: Uuid,
        placeRatingRequest: PlaceRatingRequest
    ): DBPlaceRating {
        return with(placeRatingRequest) {
            DBPlaceRating(
                id = generateId,
                placeId = placeId,
                ownerId = ownerId,
                message = comment,
                rating = rating,
                placedAt = Date().time,
                lastEditedAt = null,
            )
        }
    }

    fun toDomain(
        joinedData: ListPlacesRatings,
    ): PlaceRating {
        return with(joinedData) {
            PlaceRating(
                id = id,
                placeId = placeId,
                comment = message,
                rating = rating,
                createdAt = placedAt,
                updatedAt = lastEditedAt,
                ownerData = User(
                    id = id_,
                    name = name,
                    email = email,
                    tag = tag,
                    avatarUrl = avatarUrl,
                )
            )
        }
    }

    fun toDomain(
        joinedData: RetrieveRating,
    ): PlaceRating {
        return with(joinedData) {
            PlaceRating(
                id = generateId,
                placeId = placeId,
                comment = message,
                rating = rating,
                createdAt = placedAt,
                updatedAt = lastEditedAt,
                ownerData = User(
                    id = id_,
                    name = name,
                    email = email,
                    tag = tag,
                    avatarUrl = avatarUrl,
                )
            )
        }
    }

    fun toDomain(joinedData: ListPlaces): Place {
        return with(joinedData) {
            Place(
                id = id,
                owner = User(
                    id = id__,
                    email = email,
                    name = name,
                    tag = tag,
                    avatarUrl = avatarUrl,
                ),
                displayName = name,
                description = description,
                address = PlaceAddress(
                    id = id_,
                    street = street,
                    city = city,
                    country = country,
                    coordinates = PlaceAddress.PlaceCoordinate(
                        latitude = latitude,
                        longitude = longitude,
                    )
                ),
                imageUrls = images?.split(LIST_SEPARATOR).orEmpty(),
                seasons = seasons.split(LIST_SEPARATOR).sanitizeSeasonList(),
                tags = seasons.split(LIST_SEPARATOR).sanitizeTagList(),
                state = state.sanitizeState(),
                createdAt = createdAt,
            )
        }
    }

    fun toDomain(
        joinedData: FindPlaceById,
    ): Place {
        return with(joinedData) {
            Place(
                id = id,
                owner = User(
                    id = id__,
                    email = email,
                    name = name,
                    tag = tag,
                    avatarUrl = avatarUrl,
                ),
                displayName = name,
                description = description,
                address = PlaceAddress(
                    id = id_,
                    street = street,
                    city = city,
                    country = country,
                    coordinates = PlaceAddress.PlaceCoordinate(
                        latitude = latitude,
                        longitude = longitude,
                    )
                ),
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
                ownerId = owner.id,
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
