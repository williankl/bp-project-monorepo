package williankl.bpProject.common.features.places.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.features.places.Divider
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.create.components.ChipCarrousselOption
import williankl.bpProject.common.features.places.create.components.ChipOption
import williankl.bpProject.common.features.places.create.components.InputOption
import williankl.bpProject.common.features.places.create.handler.CreationHandler
import williankl.bpProject.common.features.places.create.handler.LocalPlaceCreationHandler
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchScreen
import williankl.bpProject.common.platform.design.components.ImagePager
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

internal data class PlaceCreationScreen(
    private val imageUriList: List<String>
) : BeautifulScreen() {
    @Composable
    override fun BeautifulContent() {
        val navigator = LocalNavigator.currentOrThrow
        val runnerModel = rememberScreenModel<PhotoSelectionRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()
        val creationHandler = remember {
            CreationHandler()
        }

        LaunchedEffect(imageUriList) {
            runnerModel.retrievePresentation(imageUriList)
        }

        CompositionLocalProvider(
            LocalPlaceCreationHandler provides creationHandler,
        ) {
            PlaceCreationContent(
                images = presentation.images,
                onBackRequested = navigator::pop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PlaceCreationContent(
        images: List<ImageBitmap>,
        onBackRequested: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current.placeCreationStrings
        val creationHandler = LocalPlaceCreationHandler.current

        Column(
            modifier = modifier
                .background(BeautifulColor.Background.composeColor),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp),
                modifier = Modifier.weight(2f),
            ) {
                stickyHeader {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_chevron_left),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon(padding = 16.dp) { onBackRequested() }
                            .size(30.dp)
                    )
                }

                item {
                    ImagePager(
                        images = images,
                        contentPadding = PaddingValues(horizontal = 26.dp),
                        pageSpacing = 28.dp,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Divider()
                }

                item {
                    LocationOptions(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(vertical = 10.dp),
                    )

                    Divider()
                }

                item {
                    SeasonsOption(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(vertical = 10.dp),
                    )

                    Divider()
                }

                item {
                    SeasonsOption(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(vertical = 10.dp),
                    )

                    Divider()
                }

                item {
                    InputOption(
                        headerPainter = painterResource(SharedDesignCoreResources.images.ic_help_circle),
                        text = creationHandler.notes,
                        onTextChange = { creationHandler.notes = it },
                        hint = strings.curiosityInputHintLabel,
                        inputHeight = 60.dp,
                        modifier = Modifier.padding(10.dp),
                    )

                    Divider()
                }

                item {
                    InputOption(
                        headerPainter = painterResource(SharedDesignCoreResources.images.ic_tags),
                        text = creationHandler.price,
                        onTextChange = { creationHandler.price = it },
                        hint = strings.costInputHintLabel,
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.padding(10.dp),
                    )

                    Divider()
                }
            }
        }
    }

    @Composable
    private fun LocationOptions(
        modifier: Modifier = Modifier,
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val strings = LocalPlacesStrings.current.placeCreationStrings

        val options = remember {
            listOf(
                ChipOption(
                    label = strings.searchLocationLabel,
                    isSelected = false,
                    onClicked = {
                        navigator.push(
                            PlaceSearchScreen { selectedPlace ->
                                println(selectedPlace) // todo - handle place selection
                            }
                        )
                    },
                )
            )
        }

        ChipCarrousselOption(
            label = strings.locationLabel,
            headerPainter = painterResource(SharedDesignCoreResources.images.ic_location_pin),
            options = options,
            modifier = modifier.fillMaxWidth()
        )
    }

    @Composable
    private fun SeasonsOption(
        modifier: Modifier = Modifier,
    ) {
        val creationHandler = LocalPlaceCreationHandler.current
        val strings = LocalPlacesStrings.current.placeCreationStrings

        val options = Season.entries.map { season ->
            ChipOption(
                label = season.label(),
                isSelected = season in creationHandler.selectedSeasons,
                onClicked = {
                    if (season in creationHandler.selectedSeasons) {
                        creationHandler.selectedSeasons.remove(season)
                    } else {
                        creationHandler.selectedSeasons.add(season)
                    }
                },
            )
        }

        ChipCarrousselOption(
            label = strings.seasonLabel,
            headerPainter = painterResource(SharedDesignCoreResources.images.ic_flower),
            options = options,
            modifier = modifier.fillMaxWidth()
        )
    }
}
