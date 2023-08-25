package williankl.bpProject.common.features.dashboard

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class DashboardRunnerModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    dispatcher = dispatcher,
    initialData = Unit,
)
