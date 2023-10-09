package williankl.bpProject.common.application.internal

import org.kodein.di.DirectDI
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.NetworkClientSession
import williankl.bpProject.common.data.sessionHandler.PreferencesHandler

internal fun DirectDI.attachClientBearerToken() {
    val preferencesHandler = instance<PreferencesHandler>()
    val networkClientSession = instance<NetworkClientSession>()
    preferencesHandler.userBearerToken()
        ?.let(networkClientSession::attachBearer)
}
