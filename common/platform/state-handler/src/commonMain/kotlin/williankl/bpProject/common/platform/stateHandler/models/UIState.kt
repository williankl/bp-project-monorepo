package williankl.bpProject.common.platform.stateHandler.models

public data class UIState<T>(
    val modelState: ModelState,
    val content: T,
)
