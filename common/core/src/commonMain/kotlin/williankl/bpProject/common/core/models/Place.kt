package williankl.bpProject.common.core.models

import kotlinx.serialization.Serializable

@Serializable
public data class Place(
    val id: String,
    val ownerId: String,
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
    ){
        @Serializable
        public data class PlaceAddressCoordinate(
            val latitude: Double,
            val longitude: Double,
        )
    }
}
