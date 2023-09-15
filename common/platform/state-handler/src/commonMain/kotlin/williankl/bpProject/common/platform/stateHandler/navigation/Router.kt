package williankl.bpProject.common.platform.stateHandler.navigation

import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination

public interface Router {
    public val navigator: Navigator
    public val bottomSheetNavigator: BottomSheetNavigator
    public val isBottomSheetVisible: Boolean
    public val isSidebarVisible: Boolean
    public fun showBottomSheet(destination: NavigationDestination)
    public fun hideBottomSheet()
    public fun push(destination: NavigationDestination)
    public fun pop()
    public fun showSidebar(destination: NavigationDestination)
    public fun hideSidebar()
}
