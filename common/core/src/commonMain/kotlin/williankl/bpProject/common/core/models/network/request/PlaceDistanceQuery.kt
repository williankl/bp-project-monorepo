package williankl.bpProject.common.core.models.network.request

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.Place.PlaceAddress.PlaceCoordinate

@Serializable
public data class PlaceDistanceQuery(
    val coordinates: PlaceCoordinate,
    val coordinatePadding: Double = 10.0,
)
