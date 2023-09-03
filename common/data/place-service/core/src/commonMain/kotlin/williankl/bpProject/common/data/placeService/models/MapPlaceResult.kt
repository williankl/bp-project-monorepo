package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate

@Serializable
public data class MapPlaceResult(
    val id: String,
    val displayName: String,
    val coordinate: MapCoordinate,
)
