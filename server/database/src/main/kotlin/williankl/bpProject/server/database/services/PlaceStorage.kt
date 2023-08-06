package williankl.bpProject.server.database.services

import java.util.UUID
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceDataAddress

public interface PlaceStorage {
    public suspend fun savePlace(place: Place)

    public suspend fun retrievePlaces(
        page: Int,
        limit: Int,
    ): List<Place>

    public suspend fun updatePlaceName(
        id: UUID,
        name: String,
    )

    public suspend fun updatePlaceDescription(
        id: UUID,
        description: String,
    )
    public suspend fun updatePlaceLocation(
        id: UUID,
        address: PlaceDataAddress,
    )
}