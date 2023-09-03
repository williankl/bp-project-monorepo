package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient

internal class MapsPlacesInfrastructure(
    private val client: HttpClient,
) {

    internal enum class MapsPlaceTypes(
        val key: String,
    ) {
        Id("places.id"),
        Name("places.name"),
        DisplayName("places.displayName"),
        Location("places.location"),
        Photos("places.photos"),
    }

    private companion object {
        const val TEXT_SEARCH_ENDPOINT = "/v1/places:searchText"
    }

}
