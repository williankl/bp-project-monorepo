package williankl.bpProject.common.data.placeService.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.networking.core.handleListResponse
import williankl.bpProject.common.data.placeService.models.DistanceRequest
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.services.MapsService
import williankl.bpProject.common.data.placeService.services.UserLocationService

internal class ClientMapsServiceInfrastructure(
    private val client: HttpClient,
    private val userLocationService: UserLocationService,
) : MapsService {

    companion object {
        const val SEARCH_ENDPOINT = "/maps/search"
        const val DISTANCE_ENDPOINT = "/maps/distance"
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

    override suspend fun distanceBetween(
        from: MapCoordinate,
        vararg to: MapCoordinate
    ): List<Long?> {
        return userLocationService.lastUserCoordinates()
            ?.let { userCoordinate ->
                client.post(DISTANCE_ENDPOINT) {
                    setBody(
                        DistanceRequest(
                            userCoordinate = userCoordinate,
                            destinationCoordinates = to.toList()
                        )
                    )
                }.handleListResponse<Long?>()
            }.orEmpty()
    }
}
