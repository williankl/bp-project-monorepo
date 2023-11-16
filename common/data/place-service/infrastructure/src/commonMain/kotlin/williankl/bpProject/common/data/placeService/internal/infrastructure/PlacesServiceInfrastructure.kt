package williankl.bpProject.common.data.placeService.internal.infrastructure

import com.benasher44.uuid.Uuid
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.data.networking.core.models.request.PlaceDistanceQuery
import williankl.bpProject.common.data.networking.core.models.request.SavingPlaceRequest
import williankl.bpProject.common.data.networking.handleListResponse
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.data.placeService.services.UserLocationService

internal class PlacesServiceInfrastructure(
    private val client: HttpClient,
    private val userLocationService: UserLocationService,
) : PlacesService {

    private companion object {
        const val PLACES_ENDPOINT = "/places"
        const val PLACES_FAVOURITE_ENDPOINT = "/places/favourite"
    }

    override suspend fun saveNewPlace(place: SavingPlaceRequest) {
        client.post(PLACES_ENDPOINT) {
            setBody(place)
        }
    }

    override suspend fun retrievePlace(id: Uuid): Place {
        return client.get(PLACES_ENDPOINT) {
            parameter("placeId", id.toString())
        }.body()
    }

    override suspend fun isPlaceFavourite(id: Uuid): Boolean {
        return client.get(PLACES_FAVOURITE_ENDPOINT) {
            parameter("placeId", id)
        }.body()
    }

    override suspend fun toggleFavouriteTo(id: Uuid, to: Boolean) {
        client.post(PLACES_FAVOURITE_ENDPOINT) {
            parameter("placeId", id)
            parameter("settingTo", to)
        }
    }

    override suspend fun retrievePlaces(
        page: Int,
        state: Place.PlaceState,
        fromUser: Uuid?,
        qualifier: PlaceQualifier?,
        distance: PlaceDistanceQuery?,
        limit: Int,
        filterFavourites: Boolean
    ): List<Place> {
        val actualUrl =
            if (filterFavourites || qualifier == PlaceQualifier.Favourite) PLACES_FAVOURITE_ENDPOINT
            else PLACES_ENDPOINT

        return client.get(actualUrl) {
            parameter("page", page)
            parameter("limit", limit)
            parameter("state", state)

            if (distance != null || qualifier == PlaceQualifier.Nearby) {
                val actualDistance = distance ?: defaultDistanceRequest()
                parameter("lat", actualDistance.coordinates.latitude)
                parameter("lon", actualDistance.coordinates.longitude)
                parameter("distance", actualDistance.maxDistance)
            }
        }.handleListResponse()
    }

    private suspend fun defaultDistanceRequest(): PlaceDistanceQuery {
        return PlaceDistanceQuery(
            coordinates = userLocationService.lastUserCoordinates() ?: error("Could not retrieve user location"),
            maxDistance = 10_000.0 // 10km
        )
    }
}
