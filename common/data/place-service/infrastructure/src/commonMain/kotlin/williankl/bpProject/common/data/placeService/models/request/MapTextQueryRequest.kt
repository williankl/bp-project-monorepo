package williankl.bpProject.common.data.placeService.models.request

import kotlinx.serialization.Serializable

@Serializable
internal data class MapTextQueryRequest(
    val textQuery: String,
    val maxResultCount: Int = 5,
)
