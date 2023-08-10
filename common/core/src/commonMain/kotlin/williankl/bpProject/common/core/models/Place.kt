package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class Place(
    @Serializable(UuidSerializer::class) val id: Uuid,
    @Serializable(UuidSerializer::class) val ownerId: Uuid,
    val name: String,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val season: PlaceSeason = PlaceSeason.Undefined,

) {
    public enum class PlaceSeason {
        Fall, Autumn, Summer, Spring, Undefined
    }

    public enum class PlaceTag {
        Nature, City, Beach, Mountains, CountrySide,
    }

    @Serializable
    public data class PlaceAddress(
        val id: String,
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
