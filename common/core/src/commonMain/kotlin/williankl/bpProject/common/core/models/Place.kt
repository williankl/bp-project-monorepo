package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class Place(
    @Serializable(UuidSerializer::class) val id: Uuid,
    @Serializable(UuidSerializer::class) val ownerId: Uuid,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val seasons: List<Season> = emptyList(),

) {
    public enum class PlaceTag {
        Nature, City, Beach, Mountains, CountrySide,
    }

    @Serializable
    public data class PlaceAddress(
        @Serializable(UuidSerializer::class) val id: Uuid,
        val street: String,
        val city: String,
        val country: String,
        val coordinates: PlaceAddressCoordinate,
    ) {
        @Serializable
        public data class PlaceAddressCoordinate(
            val latitude: Double,
            val longitude: Double,
        )
    }
}
