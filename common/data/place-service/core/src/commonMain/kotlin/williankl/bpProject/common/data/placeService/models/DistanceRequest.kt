package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate

@Serializable
public data class DistanceRequest(
    val userCoordinate: MapCoordinate,
    val destinationCoordinates: List<MapCoordinate>,
)
