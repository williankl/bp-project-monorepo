package williankl.bpProject.common.application.internal

import org.kodein.di.DI
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.NetworkClientSession
import williankl.bpProject.common.data.sessionHandler.PreferencesHandler

internal fun DI.attachClientBearerToken() {
    val preferencesHandler by instance<PreferencesHandler>()
    val networkClientSession by instance<NetworkClientSession>()
    preferencesHandler.userBearerToken()
        ?.let(networkClientSession::attachBearer)
}
