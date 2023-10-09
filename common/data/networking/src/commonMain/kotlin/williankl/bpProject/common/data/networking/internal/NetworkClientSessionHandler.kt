package williankl.bpProject.common.data.networking.internal

import kotlinx.coroutines.flow.MutableStateFlow
import williankl.bpProject.common.data.networking.NetworkClientSession

internal class NetworkClientSessionHandler : NetworkClientSession {

    private val currentToken = MutableStateFlow<String?>(null)
    override fun currentToken(): String? {
        return currentToken.value
    }

    override fun attachBearer(token: String) {
        currentToken.value = token
    }

    override fun clearBearer() {
        currentToken.value = null
    }
}
