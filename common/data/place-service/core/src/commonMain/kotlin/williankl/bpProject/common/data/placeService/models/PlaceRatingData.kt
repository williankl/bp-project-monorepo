package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable

@Serializable
public data class PlaceRatingData(
    val rating: Float,
    val ratingCount: Int,
)
