package williankl.bpProject.common.features.places.str

internal data class PlacesStrings(
    val photoSelectionStrings: PhotoSelectionStrings,
    val placeCreationStrings: PlaceCreationStrings,
) {
    internal data class PhotoSelectionStrings(
        val nextActionLabel: String,
    )

    internal data class PlaceCreationStrings(
        val seasonLabel: String,
        val curiosityInputHintLabel: String,
        val costInputHintLabel: String,
    )
}
