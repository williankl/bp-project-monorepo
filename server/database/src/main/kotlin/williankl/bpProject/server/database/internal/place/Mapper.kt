package williankl.bpProject.server.database.internal.place

import com.benasher44.uuid.Uuid
import place.FindPlaceById
import place.ListPlaces
import place.ListPlacesRatings
import place.PlaceAddressData
import place.PlaceData
import place.RetrieveRating
import williankl.bpProject.common.core.generateId
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.*
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import java.util.Date
import place.PlaceRating as DBPlaceRating

internal object Mapper {

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

    fun toDomain(
        joinedData: ListPlaces,
        images: List<ImageData>,
    ): Place {
        return with(joinedData) {
            Place(
                id = id,
                owner = User(
                    id = id__,
                    email = email,
                    name = name_,
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
                    coordinates = MapCoordinate(
                        latitude = latitude,
                        longitude = longitude,
                    )
                ),
                images = images,
                seasons = seasons.map { seasonName -> seasonName.sanitizeSeason() },
                tags = tags.map { tagName -> tagName.sanitizeTag() },
                state = state.sanitizeState(),
                createdAt = createdAt,
            )
        }
    }

    fun toDomain(
        joinedData: FindPlaceById,
        images: List<ImageData>,
    ): Place {
        return with(joinedData) {
            Place(
                id = id,
                owner = User(
                    id = id__,
                    email = email,
                    name = name_,
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
                    coordinates = MapCoordinate(
                        latitude = latitude,
                        longitude = longitude,
                    )
                ),
                images = images,
                seasons = seasons.map { seasonName -> seasonName.sanitizeSeason() },
                tags = tags.map { tagName -> tagName.sanitizeTag() },
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
                seasons = seasons
                    .map { season -> season.name }
                    .toTypedArray(),
                tags = tags
                    .map { tag -> tag.name }
                    .toTypedArray(),
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

    internal fun String.sanitizeSeason(): Season {
        return Season.entries
            .firstOrNull { entry -> entry.name == this }
            ?: error("$this not mapped as season")
    }

    internal fun String.sanitizeTag(): PlaceTag {
        return PlaceTag.entries
            .firstOrNull { entry -> entry.name == this }
            ?: error("$this not mapped as a place tag")
    }

    internal fun String.sanitizeState(): PlaceState {
        return PlaceState.entries
            .firstOrNull { entry -> entry.name == this }
            ?: error("$this not mapped as place state")
    }
}
