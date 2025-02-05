package williankl.bpProject.server.database.internal.favourite

import favourite.RetrieveFavouritePlaces
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.User
import williankl.bpProject.server.database.internal.place.Mapper.sanitizeSeason
import williankl.bpProject.server.database.internal.place.Mapper.sanitizeState
import williankl.bpProject.server.database.internal.place.Mapper.sanitizeTag

internal object Mapper {
    fun toDomain(
        joinedData: RetrieveFavouritePlaces,
        images: List<Place.ImageData>,
    ): Place {
        return with(joinedData) {
            Place(
                id = placeId,
                owner = User(
                    id = ownerId,
                    email = email,
                    name = name,
                    tag = tag,
                    avatarUrl = avatarUrl,
                ),
                displayName = name_,
                description = description,
                address = Place.PlaceAddress(
                    id = id__,
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
}
