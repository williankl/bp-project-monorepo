package williankl.bpProject.common.data.placeService.services

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceState
import williankl.bpProject.common.core.models.network.request.PlaceDistanceQuery
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest

public interface PlacesService {

    public suspend fun saveNewPlace(place: SavingPlaceRequest)

    public suspend fun retrievePlace(id: Uuid): Place

    public suspend fun isPlaceFavourite(id: Uuid): Boolean

    public suspend fun toggleFavouriteTo(
        id: Uuid,
        to: Boolean,
    )

    public suspend fun retrievePlaces(
        page: Int,
        state: PlaceState = PlaceState.Published,
        fromUser: Uuid? = null,
        distance: PlaceDistanceQuery? = null,
        limit: Int = 20,
        filterFavourites: Boolean = false,
    ): List<Place>
}
