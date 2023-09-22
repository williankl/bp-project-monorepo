package williankl.bpProject.server.app

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuidFrom
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import io.ktor.util.pipeline.PipelineContext
import williankl.bpProject.common.core.runOrNullSuspend

internal val ApplicationCall.userId: Uuid?
    get() = principal<UserIdPrincipal>()
        ?.name
        ?.let(::uuidFrom)

internal suspend fun PipelineContext<*, ApplicationCall>.idFromParameter(
    label: String
): Uuid? = runOrNullSuspend {
    call.parameters[label]
        ?.let(::uuidFrom)
}
