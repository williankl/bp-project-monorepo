package williankl.bpProject.server.app

import java.util.UUID

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