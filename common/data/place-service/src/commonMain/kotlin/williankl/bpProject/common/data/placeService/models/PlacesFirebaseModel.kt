package williankl.bpProject.common.data.placeService.models

internal data class PlacesFirebaseModel(
    val id: String,
    val ownerId: String,
    val addressId: String,
    val name: String,
    val description: String,
    val season: String,
    val imageUrls: List<String>,
)