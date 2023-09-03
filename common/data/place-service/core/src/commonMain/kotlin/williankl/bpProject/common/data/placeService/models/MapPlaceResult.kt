package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable

@Serializable
public data class MapPlaceResult(
    val id: String,
    val displayName: String,
)
