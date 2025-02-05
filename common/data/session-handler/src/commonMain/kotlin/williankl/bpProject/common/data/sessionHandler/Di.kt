package williankl.bpProject.common.data.sessionHandler

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.sessionHandler.internal.SessionHandler

public expect val platformSessionHandlerDi: DI.Module

public val sessionHandlerDi: DI.Module = DI.Module("williankl.bpProject.common.data.sessionHandler") {
    bindSingleton<Session> {
        SessionHandler(
            client = instance(),
            preferencesHandler = instance(),
        )
    }
}
