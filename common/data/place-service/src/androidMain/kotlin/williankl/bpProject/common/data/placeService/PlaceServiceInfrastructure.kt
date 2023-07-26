package williankl.bpProject.common.data.placeService

import williankl.bpProject.common.core.models.PlaceData
import williankl.bpProject.common.core.services.PlaceService

internal class PlaceServiceInfrastructure: PlaceService {
    override suspend fun createPlace(data: PlaceData) {
        TODO("Not yet implemented")
    }

    override suspend fun retrievePlace(id: String): PlaceData {
        TODO("Not yet implemented")
    }
}