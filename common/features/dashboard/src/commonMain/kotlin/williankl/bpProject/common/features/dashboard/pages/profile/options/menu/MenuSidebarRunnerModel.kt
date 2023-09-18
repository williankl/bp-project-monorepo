package williankl.bpProject.common.features.dashboard.pages.profile.options.menu

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.features.dashboard.pages.profile.options.menu.MenuSidebarRunnerModel.MenuSidebarPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class MenuSidebarRunnerModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<MenuSidebarPresentation>(
    dispatcher = dispatcher,
    initialData = MenuSidebarPresentation()
) {
    internal data class MenuSidebarPresentation(
        val avatarUrl: String = "",
        val userFullName: String = "",
        val userTag: String = "",
    )
}
