package williankl.bpProject.server.database.internal.image

import com.benasher44.uuid.Uuid
import place.ImageData
import williankl.bpProject.common.core.models.Place

internal object Mapper {

    fun toDomain(from: ImageData): Place.ImageData {
        return with(from) {
            Place.ImageData(
                id = id,
                url = url,
                position = position,
            )
        }
    }

    fun fromDomain(
        placeId: Uuid,
        data: Place.ImageData,
    ): ImageData {
        return with(data) {
            ImageData(
                id = id,
                placeId = placeId,
                url = url,
                position = position,
            )
        }
    }
}
