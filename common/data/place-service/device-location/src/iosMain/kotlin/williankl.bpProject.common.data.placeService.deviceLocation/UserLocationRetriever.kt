package williankl.bpProject.common.data.placeService.deviceLocation

import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.UserLocationService

internal actual class UserLocationRetriever : UserLocationService {
    override suspend fun currentUserCoordinates(): MapCoordinate {
        TODO("Not yet implemented")
    }

    override suspend fun lastUserCoordinates(): MapCoordinate {
        TODO("Not yet implemented")
    }
}
