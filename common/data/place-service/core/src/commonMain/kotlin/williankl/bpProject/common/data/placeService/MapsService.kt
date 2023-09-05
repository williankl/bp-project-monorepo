package williankl.bpProject.common.data.placeService

import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.models.SavingPlace

public interface MapsService {

    public suspend fun queryForPlace(
        query: String,
    ): List<MapPlaceResult>

    public suspend fun placeFromCoordinates(
        coordinates: MapCoordinate,
    ): MapPlaceResult
}
