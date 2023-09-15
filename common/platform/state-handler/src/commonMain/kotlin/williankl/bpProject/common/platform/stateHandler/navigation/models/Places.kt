package williankl.bpProject.common.platform.stateHandler.navigation.models

public sealed class Places : NavigationDestination() {
    public data class PlacePhotoSelection(
        val uriList: List<String>
    ) : Places()
    public data object PlaceDataCreation : Places()
    public data object PlaceLocalSearch : Places()
}
