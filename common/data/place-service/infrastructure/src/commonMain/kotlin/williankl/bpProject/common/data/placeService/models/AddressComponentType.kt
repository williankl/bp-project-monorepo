package williankl.bpProject.common.data.placeService.models

internal enum class AddressComponentType(
    val key: String,
) {
    AdministrativeAreaLevelOne("administrative_area_level_1"),
    AdministrativeAreaLevelTwo("administrative_area_level_2"),
    AdministrativeAreaLevelThree("administrative_area_level_3"),
    Archipelago("archipelago"),
    ColloquialArea("colloquial_area"),
    Continent("continent"),
    Landmark("landmark"),
    NaturalFeature("natural_feature"),
    Neighborhood("neighborhood"),
    PointOfInterest("point_of_interest"),
    Route("route"),
    StreetAddress("street_address"),
    StreetNumber("street_number"),
    Locality("locality"),
    SubLocality("sublocality"),
    SubLocalityOne("sublocality_level_1"),
    SubLocalityTwo("sublocality_level_2"),
    SubPremise("subpremise"),
    TownSquare("town_square"),
    Country("country"),
    Unknown("unknown"),
}
