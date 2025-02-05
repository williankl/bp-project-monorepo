package williankl.bpProject.common.data.placeService.internal.infrastructure

import com.benasher44.uuid.Uuid
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.core.models.network.request.PlaceRatingRequest
import williankl.bpProject.common.data.networking.handleListResponse
import williankl.bpProject.common.data.placeService.models.PlaceRatingData
import williankl.bpProject.common.data.placeService.services.PlaceRatingService

internal class PlaceRatingServiceInfrastructure(
    private val client: HttpClient,
) : PlaceRatingService {

    private companion object {
        const val PLACES_ENDPOINT = "/places/rating"
        const val PLACES_METADATA_ENDPOINT = "/places/rating/metadata"
    }

    override suspend fun ratePlace(
        placeId: Uuid,
        rateRequest: PlaceRatingRequest,
    ): PlaceRating {
        return client.post(PLACES_ENDPOINT) {
            parameter("placeId", placeId.toString())
            setBody(rateRequest)
        }.body()
    }

    override suspend fun placeRatingData(placeId: Uuid): PlaceRatingData {
        return client.get(PLACES_METADATA_ENDPOINT) {
            parameter("placeId", placeId.toString())
        }.body()
    }

    override suspend fun ratingsForPlace(
        placeId: Uuid,
        page: Int,
        limit: Int
    ): List<PlaceRating> {
        return client.get(PLACES_ENDPOINT) {
            parameter("placeId", placeId.toString())
            parameter("page", page)
            parameter("limit", limit)
        }.handleListResponse()
    }

    override suspend fun deleteRating(ratingId: Uuid) {
        client.delete(PLACES_ENDPOINT) {
            parameter("placeId", ratingId.toString())
        }
    }
}
