package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable

@Serializable
internal data class MapTextQueryResponse(
    val places: List<MapTextQueryResponseData>
) {
    @Serializable
    internal data class MapTextQueryResponseData(
        val id: String,
        val formattedAddress: String,
        val displayName: DisplayNameResponse,
    ) {
        @Serializable
        internal data class DisplayNameResponse(
            val text: String,
            val languageCode: String,
        )
    }
}