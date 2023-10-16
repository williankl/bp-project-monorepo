package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class Place(
    @Serializable(UuidSerializer::class) val id: Uuid,
    val owner: User,
    val displayName: String,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val tags: List<PlaceTag>,
    val state: PlaceState,
    val seasons: List<Season> = emptyList(),
    val createdAt: Long,
) {

    @Serializable
    public enum class PlaceTag {
        @SerialName("nature")
        Nature,

        @SerialName("city")
        City,

        @SerialName("beach")
        Beach,

        @SerialName("mountains")
        Mountains,

        @SerialName("countrySide")
        CountrySide,
    }

    @Serializable
    public enum class PlaceState {
        @SerialName("published")
        Published,

        @SerialName("private")
        Private,

        @SerialName("disabled")
        Disabled
    }

    @Serializable
    public data class PlaceAddress(
        @Serializable(UuidSerializer::class) val id: Uuid,
        val street: String,
        val city: String,
        val country: String,
        val coordinates: MapCoordinate,
    )
}
