package williankl.bpProject.common.platform.stateHandler.models

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

public sealed class ModelState : JavaSerializable {
    public data class Error(
        val reason: Throwable
    ) : ModelState()

    public data object Loading : ModelState()

    public data object Content : ModelState()
}
