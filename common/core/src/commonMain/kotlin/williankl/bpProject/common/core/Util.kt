package williankl.bpProject.common.core

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import williankl.bpProject.common.core.models.Place.PlaceAddress.PlaceCoordinate

public val generateId: Uuid
    get() = uuid4()

public fun PlaceCoordinate.inRangeOf(
    other: PlaceCoordinate,
    padding: Double,
): Boolean {
    val latRange = (other.latitude - padding)..(other.latitude + padding)
    val lonRange = (other.longitude - padding)..(other.longitude + padding)
    return latitude in latRange && longitude in lonRange
}

public fun <T> runOrNull(
    action: () -> T
): T? {
    return runCatching {
        action()
    }.fold(
        onSuccess = { it },
        onFailure = { error ->
            println(error.message)
            null
        },
    )
}

public suspend fun <T> runOrNullSuspend(
    action: suspend () -> T
): T? {
    return runCatching {
        action()
    }.fold(
        onSuccess = { it },
        onFailure = { error ->
            println(error.message)
            error.printStackTrace()
            null
        },
    )
}
