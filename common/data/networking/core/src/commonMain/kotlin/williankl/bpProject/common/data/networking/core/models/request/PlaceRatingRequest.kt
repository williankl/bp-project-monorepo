package williankl.bpProject.common.data.networking.core.models.request

import kotlinx.serialization.Serializable

@Serializable
public data class PlaceRatingRequest(
    val comment: String?,
    val rating: Int,
)
