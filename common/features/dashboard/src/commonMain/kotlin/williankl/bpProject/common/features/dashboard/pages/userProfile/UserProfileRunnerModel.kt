package williankl.bpProject.common.features.dashboard.pages.userProfile

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.features.dashboard.pages.userProfile.UserProfileRunnerModel.UserProfilePresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class UserProfileRunnerModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<UserProfilePresentation>(
    dispatcher = dispatcher,
    initialData = UserProfilePresentation()
) {
    internal data class UserProfilePresentation(
        val avatarUrl: String = "",
        val userFullName: String = "",
        val userTag: String = "",
        val posts: List<Place> = emptyList()
    )
}