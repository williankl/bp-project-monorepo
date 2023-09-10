package williankl.bpProject.common.features.places.photoSelection

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.chrynan.uri.core.Uri
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.toImageBitmap
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.create.PlaceCreationScreen
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.Companion.defaultImageColor
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.platform.design.components.ActionedImagePager
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonConfig
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.models.IconConfig
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public data class PhotoSelectionScreen(
    private val imageUriList: List<String>
) : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val imageRetrievalController = LocalImageRetrievalController.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.currentOrThrow
        val runnerModel = rememberScreenModel<PhotoSelectionRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        var finalUriList by remember {
            mutableStateOf(imageUriList)
        }

        LaunchedEffect(finalUriList) {
            runnerModel.retrievePresentation(finalUriList)
        }

        PhotoSelectionContent(
            presentation = presentation,
            onDeleteRequested = runnerModel::handleImageRemoval,
            onAddRequested = {
                imageRetrievalController.showBottomSheet(bottomSheetNavigator) { result ->
                    finalUriList = finalUriList + result
                }
            },
            onImagesConfirmed = {
                navigator.push(PlaceCreationScreen(finalUriList))
            },
            onBackRequested = navigator::pop,
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PhotoSelectionContent(
        presentation: PhotoSelectionPresentation,
        onDeleteRequested: (Uri) -> Unit,
        onAddRequested: () -> Unit,
        onImagesConfirmed: () -> Unit,
        onBackRequested: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current
        val pagerState = rememberPagerState()

        var currentPageColor by remember {
            mutableStateOf(defaultImageColor)
        }

        val animatedColor by animateColorAsState(currentPageColor)

        val imageBitmapPresentation = remember(presentation.imageDataList) {
            presentation.imageDataList.associate { (uri, bitmap) ->
                uri to bitmap.toImageBitmap()
            }
        }

        LaunchedEffect(presentation.imageDataList, pagerState.currentPage) {
            currentPageColor = presentation.imageDataList.getOrNull(pagerState.currentPage)
                ?.averageColor
                ?: defaultImageColor
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(26.dp),
            modifier = modifier
                .background(BeautifulColor.Background.composeColor)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BeautifulColor.Background.composeColor,
                            animatedColor,
                            BeautifulColor.Background.composeColor,
                        )
                    )
                ),
        ) {
            ActionedImagePager(
                state = pagerState,
                imageMap = imageBitmapPresentation,
                actionResource = SharedDesignCoreResources.images.ic_trash_close,
                contentPadding = PaddingValues(horizontal = 26.dp),
                pageSpacing = 28.dp,
                onActionClicked = onDeleteRequested,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(16.dp),
            ) {
                Image(
                    painter = painterResource(SharedDesignCoreResources.images.ic_camera_plus),
                    colorFilter = ColorFilter.tint(BeautifulColor.Secondary.composeColor),
                    contentDescription = null,
                    modifier = Modifier
                        .clickableIcon { onAddRequested() }
                        .size(30.dp)
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Button(
                    label = strings.photoSelectionStrings.nextActionLabel,
                    onClick = onImagesConfirmed,
                    variant = ButtonVariant.Secondary,
                    type = ButtonType.Pill,
                    iconsOnExtremes = true,
                    config = ButtonConfig(
                        trailingIcon = IconConfig(
                            painter = painterResource(SharedDesignCoreResources.images.ic_chevron_right),
                        )
                    ),
                    modifier = Modifier.width(140.dp),
                )
            }
        }
    }
}
