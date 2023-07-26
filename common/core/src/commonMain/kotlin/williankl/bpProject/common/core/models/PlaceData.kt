package williankl.bpProject.common.core.models

public data class PlaceData(
    val id: String,
    val name: String,
    val address: PlaceDataAddress,
    val season: PlaceDataSeason = PlaceDataSeason.Undefined,

) {
    public enum class PlaceDataSeason {
        Fall, Autumn, Summer, Spring, Undefined
    }

    public enum class PlaceDataTag {
        Nature, City, Beach, Mountains, CountrySide,
    }

    public data class PlaceDataAddress(
        val street: String,
        val city: String,
        val country: String,
    )
}
