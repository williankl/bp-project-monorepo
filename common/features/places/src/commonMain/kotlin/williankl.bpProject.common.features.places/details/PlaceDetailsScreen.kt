package williankl.bpProject.common.features.places.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.platform.design.components.AsyncImagePager
import williankl.bpProject.common.platform.design.components.TextContainer
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

public class PlaceDetailsScreen(
    private val place: Place,
) : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<PlaceDetailsRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        PlaceDetailsContent(
            place = place,
            presentation = presentation,
            onOptionSelected = { option ->
                when (option) {
                    is DetailsOptions.AddRoute -> Unit
                    is DetailsOptions.Address -> Unit
                    is DetailsOptions.Favourite -> Unit
                    is DetailsOptions.Owner -> Unit
                    is DetailsOptions.Season -> Unit
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun PlaceDetailsContent(
        presentation: PlaceDetailsPresentation,
        place: Place,
        onOptionSelected: (DetailsOptions) -> Unit,
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

            OptionsContainer(
                place = place,
                onOptionSelected = onOptionSelected,
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

    @Composable
    private fun OptionsContainer(
        place: Place,
        onOptionSelected: (DetailsOptions) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val options = remember(place) {
            listOf(
                DetailsOptions.Owner(place.owner),
                DetailsOptions.Season(place.seasons),
                DetailsOptions.Address(place.address),
                DetailsOptions.Favourite,
                DetailsOptions.AddRoute,
            )
        }

        Column(
            modifier = modifier,
        ) {
            AnimatedVisibility(
                visible = place.description != null,
                modifier = Modifier,
            ) {
                place.description?.let { description ->
                    TextContainer(
                        text = description,
                        preOverflowMaxLines = 2,
                    )
                }
            }

            options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clickable { onOptionSelected(option) }
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    option.header()

                    Text(
                        text = option.label(),
                        size = TextSize.Large,
                    )
                }

                if (index == options.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .background(BeautifulColor.Border.composeColor)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}
