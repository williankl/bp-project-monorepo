package williankl.bpProject.common.features.dashboard

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import org.kodein.di.bindProvider
import williankl.bpProject.common.features.dashboard.DashboardScreen.DashboardTab
import williankl.bpProject.common.features.dashboard.pages.userProfile.UserProfileRunnerModel

public val dashboardDi: DI.Module = DI.Module("williankl.bpProject.common.features.dashboard") {
    bindMultiton<DashboardTab, DashboardRunnerModel> { tab ->
        DashboardRunnerModel(
            initialTab = tab,
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        UserProfileRunnerModel(
            dispatcher = Dispatchers.Default,
        )
    }
}
