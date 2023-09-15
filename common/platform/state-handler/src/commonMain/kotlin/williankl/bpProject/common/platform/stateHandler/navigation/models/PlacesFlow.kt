package williankl.bpProject.common.platform.stateHandler.navigation.models

public sealed class PlacesFlow : NavigationDestination() {
    public data class PlacePhotoSelection(
        val uriList: List<String>
    ) : PlacesFlow()
    public data object PlaceDataCreation : PlacesFlow()
    public data object PlaceLocalSearch : PlacesFlow()
}
