package williankl.bpProject.common.features.places.create.handler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import korlibs.time.internal.Serializable
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.data.placeService.models.MapPlaceResult

internal class CreationHandler {

    val selectedSeasons = mutableStateListOf<Season>()

    var notes by mutableStateOf("")

    var price by mutableStateOf("")

    var selectedAddress by mutableStateOf<MapPlaceResult?>(null)
}
