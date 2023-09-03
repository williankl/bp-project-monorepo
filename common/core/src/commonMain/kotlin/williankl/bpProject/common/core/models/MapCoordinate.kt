package williankl.bpProject.common.core.models

import kotlinx.serialization.Serializable

@Serializable
public data class MapCoordinate(
    val latitude: Double,
    val longitude: Double,
)
