package williankl.bpProject.common.features.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.data.sessionHandler.Session
import williankl.bpProject.common.features.dashboard.DashboardRunnerModel.DashboardPresentation
import williankl.bpProject.common.features.dashboard.DashboardScreen.DashboardTab
import williankl.bpProject.common.features.dashboard.models.DashboardActions
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class DashboardRunnerModel(
    private val session: Session,
    initialTab: DashboardTab,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<DashboardPresentation>(
    dispatcher = dispatcher,
    initialData = DashboardPresentation(),
) {

    internal data class DashboardPresentation(
        val user: User? = null,
    )

    var currentTab by mutableStateOf(
        when (initialTab) {
            DashboardTab.Home -> DashboardActions.Home
            DashboardTab.Profile -> DashboardActions.Profile
        }
    )

    fun refreshPresentation() = setContent {
        DashboardPresentation(
            user = session.loggedInUser()
        )
    }
}
