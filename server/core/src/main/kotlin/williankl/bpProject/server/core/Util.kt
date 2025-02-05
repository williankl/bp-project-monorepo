package williankl.bpProject.server.core

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuidFrom
import io.ktor.http.headers
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import io.ktor.util.pipeline.PipelineContext
import williankl.bpProject.common.core.runOrNull
import williankl.bpProject.common.core.runOrNullSuspend
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

public val ApplicationCall.userId: Uuid?
    get() = principal<UserIdPrincipal>()
        ?.name
        ?.let(::uuidFrom)

public val PipelineContext<*, ApplicationCall>.bearer: String?
    get() = call.request.headers["authorization"]

public suspend fun PipelineContext<*, ApplicationCall>.idFromParameter(
    label: String
): Uuid? = runOrNullSuspend {
    call.parameters[label]
        ?.let(::uuidFrom)
}

public fun retrieveFromEnv(key: String): String? {
    return retrieveFromLocalProperties(key)
        ?: System.getenv(key)
}

public fun retrieveFromLocalProperties(key: String): String? {
    val properties = Properties()
    val localProperties = File("local.properties")
    return runOrNull {
        val fileInputStream = FileInputStream(localProperties)
        val streamReader = InputStreamReader(fileInputStream, Charsets.UTF_8)
        properties.load(streamReader)
        properties.getProperty(key)
    }
}
