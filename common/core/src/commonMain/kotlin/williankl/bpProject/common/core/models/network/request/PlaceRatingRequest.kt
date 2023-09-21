package williankl.bpProject.common.core.models.network.request

import kotlinx.serialization.Serializable

@Serializable
public data class PlaceRatingRequest(
    val comment: String?,
    val rating: Int?,
)
