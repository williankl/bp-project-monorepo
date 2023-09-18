package williankl.bpProject.common.features.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.features.dashboard.DashboardScreen.DashboardTab
import williankl.bpProject.common.features.dashboard.models.DashboardActions
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class DashboardRunnerModel(
    initialTab: DashboardTab,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    dispatcher = dispatcher,
    initialData = Unit,
) {
    var currentTab by mutableStateOf(
        when (initialTab) {
            DashboardTab.Home -> DashboardActions.Home
            DashboardTab.Profile -> DashboardActions.Profile
        }
    )
}
