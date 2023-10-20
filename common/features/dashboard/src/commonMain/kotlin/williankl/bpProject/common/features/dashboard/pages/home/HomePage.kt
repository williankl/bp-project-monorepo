package williankl.bpProject.common.features.dashboard.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.features.dashboard.LocalDashboardStrings
import williankl.bpProject.common.features.dashboard.pages.home.HomeRunnerModel.HomePresentation.PlacePresentation
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.modifyIf
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.collectData
import williankl.bpProject.common.platform.stateHandler.navigation.models.Places
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

internal object HomePage : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() {
            val strings = LocalDashboardStrings.current
            return remember {
                ToolbarConfig(
                    label = strings.projectName,
                    backgroundColor = BeautifulColor.BackgroundHigh,
                )
            }
        }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<HomeRunnerModel>()
        val presentation by runnerModel.collectData()
        val router = LocalRouter.currentOrThrow

        HomeContent(
            presentation = presentation,
            onPlaceSelected = { place ->
                router.push(Places.PlaceDetails(place))
            },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize()
        )
    }

    @Composable
    private fun HomeContent(
        presentation: HomeRunnerModel.HomePresentation,
        onPlaceSelected: (Place) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalDashboardStrings.current.homeStrings

        LazyColumn(
            modifier = modifier,
        ) {
            if (presentation.nearestPlaces.isNotEmpty()) {
                item {
                    LabeledContent(
                        label = strings.nearestLabel,
                        withAction = { /* todo - redirect to listing screen */ },
                        modifier = Modifier,
                    ) {
                        SampleStaggeredItem(
                            places = presentation.nearestPlaces,
                            onPlaceSelected = onPlaceSelected,
                            modifier = Modifier
                                .padding(12.dp)
                                .height(400.dp),
                        )
                    }
                }
            }
            if (presentation.favouritePlaces.isNotEmpty()) {
                item {
                    LabeledContent(
                        label = strings.recentLabel,
                        modifier = Modifier,
                    ) {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(presentation.favouritePlaces) { place ->
                                SimplePlaceDisplay(
                                    place = place,
                                    modifier = Modifier.clickableIcon(0.dp) { onPlaceSelected(place) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SampleStaggeredItem(
        places: List<PlacePresentation>,
        onPlaceSelected: (Place) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        @Composable
        fun WeightedColumn(
            firstBigger: Boolean,
            places: List<PlacePresentation>,
            modifier: Modifier = Modifier,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier,
            ) {
                places.forEachIndexed { index, placePresentation ->
                    val weight = when {
                        index == 0 && firstBigger -> 1f
                        index != 0 && firstBigger.not() -> 1f
                        else -> 0.8f
                    }

                    PlaceDisplay(
                        placePresentation = placePresentation,
                        modifier = Modifier
                            .clickableIcon(0.dp) { onPlaceSelected(placePresentation.place) }
                            .weight(weight),
                    )
                }
            }
        }

        val validColumns = remember(places) {
            places.chunked(2)
                .take(2)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier,
        ) {
            validColumns.forEachIndexed { index, columnPlaces ->
                WeightedColumn(
                    firstBigger = index % 2 == 0,
                    places = columnPlaces,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    @Composable
    private fun SimplePlaceDisplay(
        place: Place,
        modifier: Modifier = Modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier,
        ) {
            AsyncImage(
                url = place.images.firstOrNull()?.url.orEmpty(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Regular.composeShape)
                    .size(100.dp),
            )

            Text(
                text = place.displayName,
                size = TextSize.XSmall,
                maxLines = 2,
                modifier = Modifier.widthIn(max = 100.dp),
            )
        }
    }

    @Composable
    private fun PlaceDisplay(
        placePresentation: PlacePresentation,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalDashboardStrings.current.homeStrings
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier,
        ) {
            AsyncImage(
                url = placePresentation.place.images.firstOrNull()?.url.orEmpty(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Regular.composeShape)
                    .weight(1f),
            )

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = placePresentation.place.displayName,
                    size = TextSize.Large,
                    weight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                )

                placePresentation.distanceLabel?.let { distance ->
                    Text(
                        text = "(${strings.distanceLabel(distance)})",
                        size = TextSize.Small,
                    )
                }
            }
        }
    }

    @Composable
    private fun LabeledContent(
        label: String,
        withAction: (() -> Unit)? = null,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        Column(
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .modifyIf(withAction != null) {
                        clickable { withAction?.invoke() }
                    }
                    .padding(12.dp),
            ) {
                Text(
                    text = label,
                    maxLines = 1,
                    weight = FontWeight.Bold,
                    size = TextSize.XXLarge
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                if (withAction != null) {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_chevron_right),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            content()
        }
    }
}
