package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import williankl.bpProject.common.data.placeService.models.MapTextQueryRequest
import williankl.bpProject.common.data.placeService.models.MapTextQueryResponse

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
        const val FIELD_MASK_HEADER = "X-Goog-FieldMask"
        const val TEXT_SEARCH_ENDPOINT = "/v1/places:searchText"
    }

    suspend fun queryForText(query: String): MapTextQueryResponse {
        val types = MapsPlaceTypes.entries

        val result = client.post(TEXT_SEARCH_ENDPOINT) {
            headers.append(
                name = FIELD_MASK_HEADER,
                value = types.joinToString(separator = ",") { type -> type.key }
            )

            setBody(
                MapTextQueryRequest(
                    textQuery = query,
                    languageCode = "pt",
                )
            )
        }.bodyAsText()

        println(result)

        return TODO()
    }

}
