package williankl.bpProject.common.data.placeService.models.response

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.internal.serializer.AddressComponentTypeSerializer
import williankl.bpProject.common.data.placeService.models.AddressComponentType

@Serializable
internal data class MapTextQueryResponse(
    val places: List<MapTextQueryResponseData> = emptyList()
) {
    @Serializable
    internal data class MapTextQueryResponseData(
        val id: String,
        val displayName: DisplayNameResponse,
        val location: MapCoordinate,
        val addressComponents: List<AddressComponents>,
    ) {
        @Serializable
        internal data class DisplayNameResponse(
            val text: String,
            val languageCode: String? = null,
        )

        @Serializable
        internal data class AddressComponents(
            val longText: String,
            val shortText: String? = null,
            val types: List<@Serializable(AddressComponentTypeSerializer::class) AddressComponentType> = emptyList(),
        )
    }
}
