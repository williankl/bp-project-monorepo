package williankl.bpProject.common.data.placeService.models

import williankl.bpProject.common.core.models.PlaceData
import williankl.bpProject.common.core.models.PlaceData.PlaceDataSeason

internal val PlaceDataSeason.firebaseTag: String
    get() = name

internal val String.placeDataSeason: PlaceDataSeason
    get() = PlaceDataSeason.valueOf(this)