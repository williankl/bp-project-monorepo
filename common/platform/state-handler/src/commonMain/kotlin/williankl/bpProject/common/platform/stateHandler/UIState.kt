package williankl.bpProject.common.platform.stateHandler

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

public sealed class UIState : JavaSerializable {
    public data class Error(
        val reason: Exception
    ) : UIState()

    public data object Loading : UIState()

    public data object Content : UIState()
}
