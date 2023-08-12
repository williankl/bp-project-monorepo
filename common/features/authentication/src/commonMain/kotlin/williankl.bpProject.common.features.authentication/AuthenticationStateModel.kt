package williankl.bpProject.common.features.authentication

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class AuthenticationStateModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    initialData = Unit,
    dispatcher = dispatcher
) {
}