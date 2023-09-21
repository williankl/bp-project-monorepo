package williankl.bpProject.server.app.routing.places

import java.util.Date
import williankl.bpProject.common.core.generateId
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.SavingPlace
import java.util.UUID

internal object PlaceMapper {
    fun SavingPlace.toPlace(ownerId: UUID): Place {
        return Place(
            id = generateId,
            ownerId = ownerId,
            displayName = name,
            description = description,
            address = address,
            imageUrls = imageUrls,
            seasons = seasons,
            tags = tags,
            state = state,
            createdAt = Date().time,
        )
    }
}
