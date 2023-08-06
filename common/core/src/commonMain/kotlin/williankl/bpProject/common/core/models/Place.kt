package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid

public data class Place(
    val id: Uuid,
    val ownerId: Uuid,
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

    public data class PlaceAddress(
        val id: Uuid,
        val street: String,
        val city: String,
        val country: String,
        val coordinates: PlaceAddressCoordinate,
    ){
        public data class PlaceAddressCoordinate(
            val latitude: Double,
            val longitude: Double,
        )
    }
}
