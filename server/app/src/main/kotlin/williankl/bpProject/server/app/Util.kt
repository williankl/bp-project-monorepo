package williankl.bpProject.server.app

import com.benasher44.uuid.Uuid

internal val generateId: Uuid
    get() = Uuid.randomUUID()

internal fun <T> parseOrNull(
    action: () -> T
): T? {
    return runCatching {
        action()
    }.fold(
        onSuccess = { it },
        onFailure = { null },
    )
}

internal suspend fun <T> parseOrNullSuspend(
    action: suspend () -> T
): T? {
    return runCatching {
        action()
    }.fold(
        onSuccess = { it },
        onFailure = { null },
    )
}
