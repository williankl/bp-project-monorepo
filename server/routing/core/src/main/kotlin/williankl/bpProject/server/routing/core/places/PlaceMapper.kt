package williankl.bpProject.server.routing.core.places

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.util.pipeline.PipelineContext
import williankl.bpProject.common.core.generateId
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.PlaceState
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.core.models.network.request.PlaceDistanceQuery
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest
import williankl.bpProject.common.core.runOrNullSuspend
import java.util.Date

internal object PlaceMapper {
    fun SavingPlaceRequest.toPlace(user: User): Place {
        return Place(
            id = generateId,
            owner = User(
                id = user.id,
                email = user.email,
                name = user.name,
                tag = user.tag,
                avatarUrl = user.avatarUrl,
            ),
            displayName = name,
            description = description,
            address = address,
            images = imageUrls,
            seasons = seasons,
            tags = tags,
            state = state,
            createdAt = Date().time,
        )
    }

    suspend fun PipelineContext<*, ApplicationCall>.retrieveStateQuery(): PlaceState? {
        return runOrNullSuspend {
            call.parameters["state"]
                ?.let(PlaceState::valueOf)
        }
    }

    suspend fun PipelineContext<*, ApplicationCall>.retrieveDistanceQuery(): PlaceDistanceQuery? {
        return runOrNullSuspend {
            val latitude = call.parameters["lat"]?.toDoubleOrNull()
            val longitude = call.parameters["lon"]?.toDoubleOrNull()
            val distance = call.parameters["distance"]?.toDoubleOrNull()

            if (latitude != null && longitude != null) {
                PlaceDistanceQuery(
                    maxDistance = distance ?: 10.0,
                    coordinates = Place.PlaceAddress.PlaceCoordinate(
                        latitude = latitude,
                        longitude = longitude,
                    )
                )
            } else null
        }
    }
}
