package williankl.bpProject.common.application.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import williankl.bpProject.common.features.authentication.AuthenticationScreen
import williankl.bpProject.common.features.authentication.modal.LoginRequiredBottomSheet
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.features.places.details.PlaceDetailsScreen
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionScreen
import williankl.bpProject.common.platform.stateHandler.navigation.Router
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination
import williankl.bpProject.common.platform.stateHandler.navigation.models.Places
import williankl.bpProject.common.platform.stateHandler.navigation.models.Profile
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal class RouterInfrastructure : Router {

    internal var mutableSideBarContent by mutableStateOf<(@Composable () -> Unit)?>(null)
    internal var mutableNavigator by mutableStateOf<Navigator?>(null)
    internal var mutableBottomSheetNavigator by mutableStateOf<BottomSheetNavigator?>(null)

    override val navigator: Navigator
        get() = mutableNavigator ?: error("Navigator was not set")

    override val bottomSheetNavigator: BottomSheetNavigator
        get() = mutableBottomSheetNavigator ?: error("BottomSheetNavigator was not set")

    override val isBottomSheetVisible: Boolean by derivedStateOf {
        runCatching { bottomSheetNavigator.isVisible }
            .fold(
                onSuccess = { it },
                onFailure = { false }
            )
    }

    override val isSidebarVisible: Boolean by derivedStateOf {
        mutableSideBarContent != null
    }

    override fun showBottomSheet(destination: NavigationDestination) {
        bottomSheetNavigator.show(destination.mapToScreen())
    }

    override fun hideBottomSheet() {
        bottomSheetNavigator.hide()
    }

    override fun push(destination: NavigationDestination) {
        navigator.push(destination.mapToScreen())
    }

    override fun replaceAll(destination: NavigationDestination) {
        navigator.replaceAll(destination.mapToScreen())
    }

    override fun pop() {
        navigator.pop()
    }

    override fun showSidebar(destination: NavigationDestination) {
        mutableSideBarContent = {
            destination
                .mapToScreen()
                .Content()
        }
    }

    override fun showSidebar(destination: BeautifulScreen) {
        mutableSideBarContent = {
            destination.Content()
        }
    }

    override fun hideSidebar() {
        mutableSideBarContent = null
    }

    private fun NavigationDestination.mapToScreen(): BeautifulScreen {
        return when (this) {
            is NavigationDestination.Dashboard -> DashboardScreen()
            is Authentication.Login -> AuthenticationScreen(startingFlow)
            is Authentication.LoginRequiredBottomSheet -> LoginRequiredBottomSheet()
            is Places.PlaceDataCreation -> TODO()
            is Places.PlaceLocalSearch -> TODO()
            is Places.PlacePhotoSelection -> PhotoSelectionScreen(uriList)
            is Places.PlaceDetails -> PlaceDetailsScreen(place)
            is Profile.UserProfile -> DashboardScreen(DashboardScreen.DashboardTab.Profile)
        }
    }
}
