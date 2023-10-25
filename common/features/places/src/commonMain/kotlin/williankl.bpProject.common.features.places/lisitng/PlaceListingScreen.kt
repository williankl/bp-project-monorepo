package williankl.bpProject.common.features.places.lisitng

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.features.places.components.PlaceDisplay
import williankl.bpProject.common.features.places.components.PlaceDisplayPresentation
import williankl.bpProject.common.platform.design.core.clickableIcon
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
        val placePaging by runnerModel.placePagingResult.collectAsState()
        val router = LocalRouter.currentOrThrow

        PlaceListingContent(
            displayPresentationList = placePaging.pagingResult.items,
            onNextPageRequested = {
                val shouldMakeRequest = placePaging.pagingResult.hasReachedFinalPage.not() &&
                    placePaging.isLoading.not()

                if (shouldMakeRequest) {
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
        displayPresentationList: List<PlaceDisplayPresentation>,
        onNextPageRequested: () -> Unit,
        onPlaceSelected: (Place) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val state = rememberLazyStaggeredGridState()
        val shouldRequestNextPage by remember {
            derivedStateOf { state.firstVisibleItemIndex > displayPresentationList.size - 10 }
        }

        LaunchedEffect(shouldRequestNextPage) {
            onNextPageRequested()
        }

        LazyVerticalStaggeredGrid(
            modifier = modifier,
            state = state,
            verticalItemSpacing = 20.dp,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp),
            columns = StaggeredGridCells.Fixed(2),
            content = {
                items(displayPresentationList) { displayPresentation ->
                    PlaceDisplay(
                        placeDisplayPresentation = displayPresentation,
                        imageModifier = Modifier,
                        modifier = Modifier
                            .clickableIcon { onPlaceSelected(displayPresentation.place) },
                    )
                }
            }
        )
    }
}
