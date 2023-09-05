package williankl.bpProject.common.features.places.create.handler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.features.places.create.model.CreatingPlaceAddress

internal class CreationHandler {

    val selectedSeasons = mutableStateListOf<Season>()

    var notes by mutableStateOf("")

    var price by mutableStateOf("")

    var inputAddress by mutableStateOf<CreatingPlaceAddress?>(null)
}
