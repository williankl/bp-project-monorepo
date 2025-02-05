package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.models.AddressComponentType
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.models.request.MapTextQueryRequest
import williankl.bpProject.common.data.placeService.models.response.DistanceMatrixResponse
import williankl.bpProject.common.data.placeService.models.response.GeoLocateResultResponse
import williankl.bpProject.common.data.placeService.models.response.MapTextQueryResponse
import williankl.bpProject.common.data.placeService.services.MapsService

internal class ServerMapsServiceInfrastructure(
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
        const val DISTANCE_MATRIX_ENDPOINT = "/maps/api/distancematrix/json"
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
                            street = typeLongTextOrEmpty(AddressComponentType.Route),
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
                            state = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelOne)
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.Locality) },
                            city = typeLongTextOrEmpty(AddressComponentType.AdministrativeAreaLevelTwo)
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.SubLocality) }
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.SubLocalityOne) },
                            neighborhood = typeLongTextOrEmpty(AddressComponentType.Neighborhood)
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.SubLocalityOne) }
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.SubLocalityTwo) },
                            country = typeLongTextOrEmpty(AddressComponentType.Country),
                            street = typeLongTextOrEmpty(AddressComponentType.Route)
                                .ifEmpty { typeLongTextOrEmpty(AddressComponentType.StreetAddress) },
                        )
                    )
                }
            }
    }

    override suspend fun distanceBetween(
        from: MapCoordinate,
        vararg to: MapCoordinate
    ): List<Long?> {
        val destinationsStr = to.joinToString(separator = "|") { destination ->
            "${destination.latitude},${destination.longitude}"
        }

        val response = mapsClient.get(DISTANCE_MATRIX_ENDPOINT) {
            parameter("origins", "${from.latitude},${from.longitude}")
            parameter("destinations", destinationsStr)
        }.body<DistanceMatrixResponse>()

        return response.rows.firstOrNull()
            ?.elements
            ?.map { matrixElement -> matrixElement.distance?.value }
            .orEmpty()
    }

    private suspend fun retrieveAddressFromCoordinate(coordinates: MapCoordinate): GeoLocateResultResponse {
        return mapsClient.get(GEO_LOCATE_ENDPOINT) {
            parameter(LAT_LNG_PARAMETER_KEY, "${coordinates.latitude},${coordinates.longitude}")
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
