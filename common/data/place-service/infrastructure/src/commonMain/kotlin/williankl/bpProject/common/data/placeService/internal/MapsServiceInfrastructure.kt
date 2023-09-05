package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.parameters
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.MapsService
import williankl.bpProject.common.data.placeService.models.AddressComponentType
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.models.request.MapTextQueryRequest
import williankl.bpProject.common.data.placeService.models.response.GeoLocateResultResponse
import williankl.bpProject.common.data.placeService.models.response.MapTextQueryResponse

internal class MapsServiceInfrastructure(
    private val placesClient: HttpClient,
    private val mapsClient: HttpClient,
) : MapsService {

    internal enum class MapsPlaceTypes(
        val key: String,
    ) {
        Id("places.id"),
        AddressComponents("places.addressComponents"),
        DisplayName("places.displayName"),
        Location("places.location"),
        Photos("places.photos"),
    }

    private companion object {
        const val FIELD_MASK_HEADER = "X-Goog-FieldMask"
        const val LAT_LNG_PARAMETER_KEY = "latlng"
        const val TEXT_SEARCH_ENDPOINT = "/v1/places:searchText"
        const val GEO_LOCATE_ENDPOINT = "/maps/api/geocode/json"
    }

    override suspend fun placeFromCoordinates(coordinates: MapCoordinate): List<MapPlaceResult> {
        return retrieveAddressFromCoordinate(coordinates)
            .results
            .map { response ->
                with(response) {
                    MapPlaceResult(
                        id = placeId,
                        displayName = displayName,
                        coordinate = coordinates,
                        address = MapPlaceResult.Address(
                            city = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelTwo),
                            state = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelOne),
                            neighborhood = typeLongTextOrEmpty(AddressComponentType.Neighborhood),
                            country = typeLongTextOrEmpty(AddressComponentType.Country),
                            street = typeLongTextOrEmpty(AddressComponentType.Street),
                        )
                    )
                }
            }
    }

    override suspend fun queryForPlace(query: String): List<MapPlaceResult> {
        return queryForText(query)
            .places
            .map { response ->
                with(response) {
                    MapPlaceResult(
                        id = id,
                        displayName = displayName.text,
                        coordinate = location,
                        address = MapPlaceResult.Address(
                            city = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelTwo),
                            state = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelOne),
                            neighborhood = typeLongTextOrEmpty(AddressComponentType.Neighborhood),
                            country = typeLongTextOrEmpty(AddressComponentType.Country),
                            street = typeLongTextOrEmpty(AddressComponentType.Street),
                        )
                    )
                }
            }
    }

    private suspend fun retrieveAddressFromCoordinate(coordinates: MapCoordinate): GeoLocateResultResponse {
        return mapsClient.get(GEO_LOCATE_ENDPOINT) {
            val parameterValue = listOf(coordinates.latitude, coordinates.longitude)
                .joinToString(separator = ",")
            url.parameters.append(LAT_LNG_PARAMETER_KEY, parameterValue)
        }.body()
    }

    private suspend fun queryForText(query: String): MapTextQueryResponse {
        val types = MapsPlaceTypes.entries

        return placesClient.post(TEXT_SEARCH_ENDPOINT) {
            headers.append(
                name = FIELD_MASK_HEADER,
                value = types.joinToString(separator = ",") { type -> type.key }
            )

            setBody(
                MapTextQueryRequest(
                    textQuery = query,
                )
            )
        }.body()
    }

    private fun GeoLocateResultResponse.MapTextQueryResponseData.typeLongTextOrEmpty(type: AddressComponentType): String {
        return addressComponents
            .firstOrNull { component -> type in component.types }
            ?.longText
            .orEmpty()
    }

    private fun MapTextQueryResponse.MapTextQueryResponseData.typeLongTextOrEmpty(type: AddressComponentType): String {
        return addressComponents
            .firstOrNull { component -> type in component.types }
            ?.longText
            .orEmpty()
    }
}
