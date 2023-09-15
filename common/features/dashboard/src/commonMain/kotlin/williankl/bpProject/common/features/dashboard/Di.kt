package williankl.bpProject.common.features.dashboard

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import williankl.bpProject.common.features.dashboard.pages.userProfile.UserProfileRunnerModel

public val dashboardDi: DI.Module = DI.Module("williankl.bpProject.common.features.dashboard") {
    bindProvider {
        DashboardRunnerModel(
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        UserProfileRunnerModel(
            dispatcher = Dispatchers.Default,
        )
    }
}
