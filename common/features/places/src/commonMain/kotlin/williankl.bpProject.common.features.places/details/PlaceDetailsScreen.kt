package williankl.bpProject.common.features.places.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.Divider
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.create.handler.CreationHandler
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.MINIMUM_SEARCH_LENGTH
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.queryDebounce
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.design.components.AsyncImagePager
import williankl.bpProject.common.platform.design.components.ImagePager
import williankl.bpProject.common.platform.design.components.maps.MapsComponent
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

internal class PlaceDetailsScreen(
    private val place: Place,
) : BeautifulScreen() {


    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<PlaceDetailsRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        PlaceDetailsContent(
            place = place,
            presentation = presentation,
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun PlaceDetailsContent(
        presentation: PlaceDetailsPresentation,
        place: Place,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            PlaceDetailsHeader(
                place = place,
                presentation = presentation,
                modifier = Modifier,
            )
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PlaceDetailsHeader(
        place: Place,
        presentation: PlaceDetailsPresentation,
        modifier: Modifier = Modifier,
    ) {
        val pagerState = rememberPagerState { place.imageUrls.size }
        var currentPageColor by remember {
            mutableStateOf(PlaceDetailsRunnerModel.defaultImageColor)
        }
        val animatedColor by animateColorAsState(currentPageColor)

        LaunchedEffect(pagerState.currentPage) {
            currentPageColor = presentation.averageColorList.getOrNull(pagerState.currentPage)
                ?: PlaceDetailsRunnerModel.defaultImageColor
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
            ) {
                Spacer(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    BeautifulColor.Transparent.composeColor,
                                    animatedColor,
                                    BeautifulColor.Transparent.composeColor,
                                )
                            )
                        )
                )

                AsyncImagePager(
                    urls = place.imageUrls,
                    state = pagerState,
                    modifier = Modifier,
                )
            }

            Text(
                text = place.displayName,
                weight = FontWeight.Bold,
                size = TextSize.Large,
            )

            Text(
                text = "${place.address.city}\n${place.address.country}",
                size = TextSize.Small,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}
