package williankl.bpProject.common.data.placeService.services

import williankl.bpProject.common.core.models.MapCoordinate

public interface UserLocationService {
    public suspend fun currentUserCoordinates(): MapCoordinate?
    public suspend fun lastUserCoordinates(): MapCoordinate?
}
