package williankl.bpProject.common.features.dashboard

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider

public val dashboardDi: DI.Module = DI.Module("williankl.bpProject.common.features.dashboard") {
    bindProvider {
        DashboardRunnerModel(
            dispatcher = Dispatchers.Default,
        )
    }
}
