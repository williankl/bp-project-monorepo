package williankl.bpProject.server.database.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place.ImageData

public interface ImageStorage {
    public fun storeImage(
        placeId: Uuid,
        data: ImageData,
    )

    public fun imagesFromPlace(
        placeId: Uuid
    ): List<ImageData>
}
