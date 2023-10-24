package williankl.bpProject.common.features.dashboard

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import org.kodein.di.bindProvider
import org.kodein.di.instance
import williankl.bpProject.common.data.placeService.models.MapServiceType
import williankl.bpProject.common.features.dashboard.DashboardScreen.DashboardTab
import williankl.bpProject.common.features.dashboard.pages.home.HomeRunnerModel
import williankl.bpProject.common.features.dashboard.pages.profile.UserProfileRunnerModel
import williankl.bpProject.common.features.dashboard.pages.profile.options.menu.MenuSidebarRunnerModel

public val dashboardDi: DI.Module = DI.Module("williankl.bpProject.common.features.dashboard") {
    bindMultiton<DashboardTab, DashboardRunnerModel> { tab ->
        DashboardRunnerModel(
            initialTab = tab,
            session = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        UserProfileRunnerModel(
            session = instance(),
            placesService = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        HomeRunnerModel(
            placeService = instance(),
            mapsService = instance(MapServiceType.Client),
            userLocationService = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        MenuSidebarRunnerModel(
            authService = instance(),
            session = instance(),
            dispatcher = Dispatchers.Default,
        )
    }
}
