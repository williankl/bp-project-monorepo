package williankl.bpProject.common.platform.stateHandler.navigation

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import kotlinx.coroutines.flow.StateFlow
import williankl.bpProject.common.platform.stateHandler.models.ModelState
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination

public interface Router {
    public val state: StateFlow<ModelState>
    public val navigator: Navigator
    public val bottomSheetNavigator: BottomSheetNavigator
    public val isBottomSheetVisible: Boolean
    public val isSidebarVisible: Boolean
    public fun showBottomSheet(destination: NavigationDestination)
    public fun hideBottomSheet()
    public fun push(destination: NavigationDestination)
    public fun replaceAll(destination: NavigationDestination)
    public fun pop()
    public fun showSidebar(destination: NavigationDestination)
    public fun showSidebar(destination: Screen)
    public fun hideSidebar()
    public fun updateUIState(modelState: ModelState)
}
