package williankl.bpProject.common.data.placeService

import com.benasher44.uuid.Uuid
import korlibs.image.bitmap.Bitmap
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.SavingPlace

public interface PlacesService {

    public suspend fun saveNewPlace(place: SavingPlace)

    public suspend fun retrievePlace(id: Uuid): Place

    public suspend fun retrievePlaces(
        page: Int,
        limit: Int,
    ): List<Place>
}
