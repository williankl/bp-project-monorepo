package williankl.bpProject.common.data.placeService

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest

public interface PlacesService {

    public suspend fun saveNewPlace(place: SavingPlaceRequest)

    public suspend fun retrievePlace(id: Uuid): Place

    public suspend fun retrievePlaces(
        page: Int,
        limit: Int,
    ): List<Place>
}
