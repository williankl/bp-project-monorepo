package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.MapCoordinate

@Serializable
public data class MapPlaceResult(
    val id: String,
    val displayName: String,
    val coordinate: MapCoordinate,
    val address: Address,
) {
    @Serializable
    public data class Address(
        val street: String,
        val country: String,
        val city: String,
        val state: String,
        val neighborhood: String,
    )
}
