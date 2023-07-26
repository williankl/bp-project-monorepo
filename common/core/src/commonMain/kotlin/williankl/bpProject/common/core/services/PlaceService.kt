package williankl.bpProject.common.core.services

import williankl.bpProject.common.core.models.PlaceData
import williankl.bpProject.common.core.models.UserData

public interface PlaceService {
    public suspend fun createPlace(
        userData: UserData,
        placeData: PlaceData,
    )
    public suspend fun retrievePlace(id: String): PlaceData?
}