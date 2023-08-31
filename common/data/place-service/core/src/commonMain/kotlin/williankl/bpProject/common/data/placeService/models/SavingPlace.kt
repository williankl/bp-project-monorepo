package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.Place.PlaceSeason

@Serializable
public data class SavingPlace(
    val name: String,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val season: PlaceSeason = PlaceSeason.Undefined,
)
