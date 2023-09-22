package williankl.bpProject.server.app

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuidFrom
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal

internal val ApplicationCall.userId: Uuid?
    get() = principal<UserIdPrincipal>()
        ?.name
        ?.let(::uuidFrom)
