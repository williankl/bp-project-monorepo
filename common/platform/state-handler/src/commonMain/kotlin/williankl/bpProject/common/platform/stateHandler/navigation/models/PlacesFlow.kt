package williankl.bpProject.common.platform.stateHandler.navigation.models

import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public sealed class PlacesFlow : NavigationDestination() {
    public data class PlacePhotoSelection(
        val uriList: List<String>
    ) : PlacesFlow()
    public data object PlaceDataCreation : PlacesFlow()
    public data object PlaceLocalSearch : PlacesFlow()
}