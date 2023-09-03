package williankl.bpProject.common.data.placeService.internal

import com.benasher44.uuid.Uuid
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.PlacesService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.models.SavingPlace

internal class PlacesServiceInfrastructure(
    private val client: HttpClient,
    private val mapsPlacesInfrastructure: MapsPlacesInfrastructure,
) : PlacesService {

    private companion object {
        const val PLACES_ENDPOINT = "/places"
    }

    override suspend fun saveNewPlace(place: SavingPlace) {
        client.post(PLACES_ENDPOINT) {
            setBody(place)
        }
    }

    override suspend fun retrievePlace(id: Uuid): Place {
        return client.get(PLACES_ENDPOINT) {
            parameter("id", id.toString())
        }.body()
    }

    override suspend fun retrievePlaces(page: Int, limit: Int): List<Place> {
        return client.get(PLACES_ENDPOINT) {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }

    override suspend fun queryForPlace(query: String): List<MapPlaceResult> {
        return mapsPlacesInfrastructure.queryForText(query)
            .places
            .map { response ->
                with(response) {
                    MapPlaceResult(
                        id = id,
                        displayName = displayName.text
                    )
                }
            }
    }
}
