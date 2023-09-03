package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable

@Serializable
internal data class MapTextQueryRequest(
    val textQuery: String,
    val languageCode: String,
    val maxResultCount: Int = 5,
)