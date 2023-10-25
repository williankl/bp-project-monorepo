package williankl.bpProject.common.data.placeService.services

import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.models.MapPlaceResult

public interface MapsService {

    public suspend fun queryForPlace(
        query: String,
    ): List<MapPlaceResult>

    public suspend fun placeFromCoordinates(
        coordinates: MapCoordinate,
    ): List<MapPlaceResult>

    public suspend fun distanceBetween(
        from: MapCoordinate,
        vararg to: MapCoordinate,
    ): List<Long?>
}
