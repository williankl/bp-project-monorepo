package williankl.bpProject.common.features.dashboard.pages.profile.options.menu

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.auth.AuthService
import williankl.bpProject.common.data.sessionHandler.Session
import williankl.bpProject.common.features.dashboard.pages.profile.options.menu.MenuSidebarRunnerModel.MenuSidebarPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class MenuSidebarRunnerModel(
    private val authService: AuthService,
    private val session: Session,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<MenuSidebarPresentation>(
    dispatcher = dispatcher,
    initialData = MenuSidebarPresentation()
) {

    init {
        refreshPresentation()
    }

    internal data class MenuSidebarPresentation(
        val userFullName: String = "",
        val userTag: String? = null,
        val avatarUrl: String? = null,
    )

    public fun logOutUser(
        onLoggedOut: () -> Unit,
    ) = runAsync {
        authService.logOut()
        session.clearSession()
        onLoggedOut()
    }

    private fun refreshPresentation() = setContent {
        val user = session.loggedInUser()
            ?: error("User should be logged in this state")

        MenuSidebarPresentation(
            userFullName = user.name,
            avatarUrl = user.avatarUrl,
            userTag = user.tag,
        )
    }
}
