package williankl.bpProject.server.app

import com.benasher44.uuid.Uuid
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import kotlinx.serialization.json.Json
import org.kodein.di.instance

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

internal suspend fun <T> runOrNull(
    action: suspend () -> T
): T? {
    return runCatching {
        action()
    }.fold(
        onSuccess = { it },
        onFailure = { error ->
            println(error.localizedMessage)
            error.printStackTrace()
            null
        },
    )
}

internal suspend inline fun <reified T> ApplicationCall.parseOrNull(): T? {
    val json by serverDi.instance<Json>()
    return runOrNull {
        json.decodeFromString(receiveText())
    }
}
