package williankl.bpProject.server.app.routing.places

import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.SavingPlace
import williankl.bpProject.server.app.generateId
import java.util.UUID

internal object PlaceMapper {
    fun SavingPlace.toPlace(ownerId: UUID): Place {
        return Place(
            id = generateId,
            ownerId = ownerId,
            name = name,
            description = description,
            address = address,
            imageUrls = imageUrls,
            season = season,
        )
    }
}
