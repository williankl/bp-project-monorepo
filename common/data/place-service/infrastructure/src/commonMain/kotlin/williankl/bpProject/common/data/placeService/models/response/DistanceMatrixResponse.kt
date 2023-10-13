package williankl.bpProject.common.data.placeService.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DistanceMatrixResponse(
    @SerialName("destination_address") val destinationAddress: List<String>,
    @SerialName("origin_addresses") val originAddresses: List<String>,
    val rows: List<DistanceMatrixRow>,
) {
    @Serializable
    internal data class DistanceMatrixRow(
        val elements: List<DistanceMatrixRowElement>,
    ) {
        @Serializable
        internal data class DistanceMatrixRowElement(
            val distance: ElementContent,
            val duration: ElementContent,
            val status: String,
        ) {
            @Serializable
            internal data class ElementContent(
                val text: String,
                val value: Long,
            )
        }
    }
}
