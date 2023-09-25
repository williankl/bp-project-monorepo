package williankl.bpProject.common.features.places.str

internal data class PlacesStrings(
    val photoSelectionStrings: PhotoSelectionStrings,
    val placeCreationStrings: PlaceCreationStrings,
    val placeSearchStrings: PlaceSearchStrings,
    val placeDetailsStrings: PlaceDetailsStrings,
) {
    internal data class PlaceDetailsStrings(
        val title: String,
        val favouriteLabel: String,
        val addToRouteLabel: String,
        val inAppRatingLabel: String,
        val commentHint: String,
    )

    internal data class PhotoSelectionStrings(
        val nextActionLabel: String,
    )

    internal data class PlaceCreationStrings(
        val locationLabel: String,
        val searchLocationLabel: String,
        val seasonLabel: String,
        val curiosityInputHintLabel: String,
        val costInputHintLabel: String,
        val publishLabel: String,
    )

    internal data class PlaceSearchStrings(
        val localizationLabel: String,
        val nextLabel: String,
    )
}
