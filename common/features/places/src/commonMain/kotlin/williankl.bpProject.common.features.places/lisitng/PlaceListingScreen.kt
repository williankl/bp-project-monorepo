package williankl.bpProject.common.features.places.lisitng

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.collectData
import williankl.bpProject.common.platform.stateHandler.navigation.models.Places
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public data class PlaceListingScreen(
    val label: String,
    val placeQualifier: PlaceQualifier,
) : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() {
            return super.toolbarConfig.copy(
                label = label,
            )
        }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<PlaceQualifier, PlaceListingRunnerModel>(arg = placeQualifier)
        val presentation by runnerModel.collectData()
        val router = LocalRouter.currentOrThrow

        PlaceListingContent(
            places = runnerModel.placePagingResult.items,
            onNextPageRequested = {
                if (runnerModel.placePagingResult.hasReachedFinalPage.not()) {
                    runnerModel.requestNextPage()
                }
            },
            onPlaceSelected = { place ->
                router.push(
                    destination = Places.PlaceDetails(place)
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun PlaceListingContent(
        places: List<Place>,
        onNextPageRequested: () -> Unit,
        onPlaceSelected: (Place) -> Unit,
        modifier: Modifier = Modifier,
    ) {

    }
}