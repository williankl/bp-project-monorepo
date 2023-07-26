package williankl.bpProject.common.core.services

import williankl.bpProject.common.core.models.PlaceData

public interface PlaceService {
    public suspend fun createPlace(data: PlaceData)
    public suspend fun retrievePlace(id: String): PlaceData
}