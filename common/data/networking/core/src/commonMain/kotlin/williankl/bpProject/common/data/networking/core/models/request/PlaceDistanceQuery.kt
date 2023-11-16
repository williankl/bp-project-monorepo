package williankl.bpProject.common.data.networking.core.models.request

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate

@Serializable
public data class PlaceDistanceQuery(
    val coordinates: MapCoordinate,
    val maxDistance: Double,
)
