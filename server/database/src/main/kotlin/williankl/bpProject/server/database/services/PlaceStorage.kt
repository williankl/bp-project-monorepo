package williankl.bpProject.server.database.services

import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceAddress
import java.util.UUID

public interface PlaceStorage {
    public suspend fun savePlace(place: Place)

    public suspend fun retrievePlaces(
        page: Int,
        limit: Int,
    ): List<Place>

    public suspend fun retrievePlace(id: UUID): Place?

    public suspend fun updatePlaceData(
        id: UUID,
        name: String,
        description: String?
    )

    public suspend fun updatePlaceLocation(
        id: UUID,
        address: PlaceAddress,
    )
}
