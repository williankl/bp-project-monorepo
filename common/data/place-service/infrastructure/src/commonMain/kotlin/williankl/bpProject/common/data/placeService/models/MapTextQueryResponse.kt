package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate

@Serializable
internal data class MapTextQueryResponse(
    val places: List<MapTextQueryResponseData>
) {
    @Serializable
    internal data class MapTextQueryResponseData(
        val id: String,
        val displayName: DisplayNameResponse,
        val location: MapCoordinate,
    ) {
        @Serializable
        internal data class DisplayNameResponse(
            val text: String,
            val languageCode: String,
        )
    }
}