package williankl.bpProject.common.data.placeService.models

internal data class PlacesAddressFirebaseModel(
    val id: String,
    val placeId: String,
    val street: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
)