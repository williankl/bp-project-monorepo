package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class Place(
    @Serializable(UuidSerializer::class) val id: Uuid,
    @Serializable(UuidSerializer::class) val ownerId: Uuid,
    val displayName: String,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val tags: List<PlaceTag>,
    val state: PlaceState,
    val seasons: List<Season> = emptyList(),
    val createdAt: Long,
) {
    public enum class PlaceTag {
        Nature, City, Beach, Mountains, CountrySide,
    }

    public enum class PlaceState {
        Published, Private, Disabled
    }

    @Serializable
    public data class PlaceAddress(
        @Serializable(UuidSerializer::class) val id: Uuid,
        val street: String,
        val city: String,
        val country: String,
        val coordinates: PlaceCoordinate,
    ) {
        @Serializable
        public data class PlaceCoordinate(
            val latitude: Double,
            val longitude: Double,
        )
    }
}
