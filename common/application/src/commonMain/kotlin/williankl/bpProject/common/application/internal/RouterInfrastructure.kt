package williankl.bpProject.common.application.internal

import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import williankl.bpProject.common.features.authentication.AuthenticationScreen
import williankl.bpProject.common.features.authentication.modal.LoginRequiredBottomSheet
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionScreen
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.navigation.Router
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination
import williankl.bpProject.common.platform.stateHandler.navigation.models.PlacesFlow

internal class RouterInfrastructure(
    override val navigator: Navigator,
    override val bottomSheetNavigator: BottomSheetNavigator,
) : Router {
    override fun showBottomSheet(destination: NavigationDestination) {
        bottomSheetNavigator.show(destination.mapToScreen())
    }

    override fun hideBottomSheet() {
        bottomSheetNavigator.hide()

    }

    override fun push(destination: NavigationDestination) {
        navigator.push(destination.mapToScreen())
    }

    override fun pop() {
        navigator.pop()
    }

    private fun NavigationDestination.mapToScreen(): BeautifulScreen {
        return when (this) {
            is NavigationDestination.Dashboard -> DashboardScreen
            is Authentication.Login -> AuthenticationScreen(startingFlow)
            is Authentication.LoginRequiredBottomSheet -> LoginRequiredBottomSheet()
            is PlacesFlow.PlaceDataCreation -> TODO()
            is PlacesFlow.PlaceLocalSearch -> TODO()
            is PlacesFlow.PlacePhotoSelection -> PhotoSelectionScreen(uriList)
        }
    }
}