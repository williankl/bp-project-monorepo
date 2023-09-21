package williankl.bpProject.common.data.placeService.models

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.Place.PlaceTag
import williankl.bpProject.common.core.models.Season

@Serializable
public data class SavingPlace(
    val name: String,
    val description: String?,
    val address: PlaceAddress,
    val imageUrls: List<String>,
    val seasons: List<Season>,
    val tags: List<PlaceTag>,
    val state: Place.PlaceState,
)
