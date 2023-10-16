package williankl.bpProject.common.core.models.network.request

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.Place.*
import williankl.bpProject.common.core.models.Season

@Serializable
public data class SavingPlaceRequest(
    val name: String,
    val description: String?,
    val address: PlaceAddress,
    val images: List<ImageData>,
    val seasons: List<Season>,
    val tags: List<PlaceTag>,
    val state: PlaceState,
)
