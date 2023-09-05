package williankl.bpProject.common.data.placeService.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import williankl.bpProject.common.data.placeService.internal.AddressComponentTypeSerializer
import williankl.bpProject.common.data.placeService.models.AddressComponentType

@Serializable
internal data class GeoLocateResultResponse(
    val results: List<MapTextQueryResponseData> = emptyList()
) {
    @Serializable
    internal data class MapTextQueryResponseData(
        @SerialName("place_id") val placeId: String,
        @SerialName("formatted_address") val displayName: String,
        @SerialName("address_components") val addressComponents: List<AddressComponents>,
    ) {
        @Serializable
        internal data class AddressComponents(
            @SerialName("long_name") val longText: String,
            @SerialName("short_name") val shortText: String,
            val types: List<@Serializable(AddressComponentTypeSerializer::class) AddressComponentType> = emptyList(),
        )
    }
}
