package williankl.bpProject.common.features.places.creating.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.features.places.Divider
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.creating.create.PlaceCreationRunnerModel.PlaceCreationPresentation
import williankl.bpProject.common.features.places.creating.create.components.ChipCarrousselOption
import williankl.bpProject.common.features.places.creating.create.components.ChipOption
import williankl.bpProject.common.features.places.creating.create.components.InputOption
import williankl.bpProject.common.features.places.creating.create.handler.LocalPlaceCreationHandler
import williankl.bpProject.common.features.places.creating.searchScreen.PlaceSearchScreen
import williankl.bpProject.common.platform.design.components.ImagePager
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.collectData
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal object PlaceCreationScreen : BeautifulScreen() {
    @Composable
    override fun BeautifulContent() {
        val router = LocalRouter.currentOrThrow
        val imageRetrievalController = LocalImageRetrievalController.currentOrThrow

        val runnerModel = rememberScreenModel<PlaceCreationRunnerModel>()
        val presentation by runnerModel.collectData()
        val images by imageRetrievalController.publishedImages.collectAsState()

        LaunchedEffect(images) {
            runnerModel.retrievePresentation(images)
        }

        CompositionLocalProvider(
            LocalPlaceCreationHandler provides runnerModel.creationHandler,
        ) {
            PlaceCreationContent(
                presentation = presentation,
                images = presentation.images,
                onPublishRequested = {
                    runnerModel.publishPlace(images) {
                        imageRetrievalController.clearPublishedImages()
                        router.replaceAll(NavigationDestination.Dashboard)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PlaceCreationContent(
        presentation: PlaceCreationPresentation,
        images: List<ImageBitmap>,
        onPublishRequested: () -> Unit,
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
                item {
                    ImagePager(
                        images = images,
                        contentPadding = PaddingValues(horizontal = 46.dp),
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
                        presentation = presentation,
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

                item {
                    Button(
                        label = strings.publishLabel,
                        variant = ButtonVariant.Primary,
                        enabled = creationHandler.selectedAddress != null,
                        onClick = onPublishRequested,
                        modifier = Modifier
                            .padding(40.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }

    @Composable
    private fun LocationOptions(
        presentation: PlaceCreationPresentation,
        modifier: Modifier = Modifier,
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val creationHandler = LocalPlaceCreationHandler.current
        val strings = LocalPlacesStrings.current.placeCreationStrings

        val suggestedOptions = remember(presentation.suggestedPlaces, creationHandler.selectedAddress) {
            presentation.suggestedPlaces.map { placeResult ->
                ChipOption(
                    label = placeResult.displayName,
                    isSelected = creationHandler.selectedAddress == placeResult,
                    onClicked = { creationHandler.selectedAddress = placeResult },
                )
            }
        }

        val selectableOption = remember(creationHandler.selectedAddress, presentation.suggestedPlaces) {
            ChipOption(
                label = creationHandler.selectedAddress?.displayName ?: strings.searchLocationLabel,
                isSelected = creationHandler.selectedAddress != null &&
                    creationHandler.selectedAddress !in presentation.suggestedPlaces,
                onClicked = {
                    navigator.push(
                        item = PlaceSearchScreen(creationHandler)
                    )
                },
            )
        }

        ChipCarrousselOption(
            label = strings.locationLabel,
            headerPainter = painterResource(SharedDesignCoreResources.images.ic_location_pin),
            options = suggestedOptions + selectableOption,
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
