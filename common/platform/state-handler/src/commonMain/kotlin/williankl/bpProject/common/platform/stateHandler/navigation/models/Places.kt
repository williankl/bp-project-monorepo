package williankl.bpProject.common.platform.stateHandler.navigation.models

import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceQualifier

public sealed class Places : NavigationDestination() {
    public data class PlacePhotoSelection(
        val uriList: List<String>
    ) : Places()
    public data object PlaceDataCreation : Places()
    public data object PlaceLocalSearch : Places()

    public data class PlaceDetails(
        val place: Place
    ) : Places()

    public data class PlaceListing(
        val label: String,
        val qualifier: PlaceQualifier,
    ) : Places()
}
