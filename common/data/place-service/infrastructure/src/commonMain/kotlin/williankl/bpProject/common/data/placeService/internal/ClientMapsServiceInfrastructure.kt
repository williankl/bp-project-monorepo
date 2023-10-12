package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.MapsService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult

internal class ClientMapsServiceInfrastructure(
    private val client: HttpClient
) : MapsService {

    companion object {
        const val SEARCH_ENDPOINT = "/maps/search"
        const val COORDINATES_ENDPOINT = "/maps"
    }

    override suspend fun queryForPlace(query: String): List<MapPlaceResult> {
        return client.get(SEARCH_ENDPOINT) {
            parameter("query", query)
        }.body()
    }

    override suspend fun placeFromCoordinates(coordinates: MapCoordinate): List<MapPlaceResult> {
        return client.get(COORDINATES_ENDPOINT) {
            parameter("latitude", coordinates.latitude)
            parameter("longitude", coordinates.longitude)
        }.body()
    }
}
