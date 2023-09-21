package williankl.bpProject.common.core

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4

public val generateId: Uuid
    get() = uuid4()

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
            null
        },
    )
}
