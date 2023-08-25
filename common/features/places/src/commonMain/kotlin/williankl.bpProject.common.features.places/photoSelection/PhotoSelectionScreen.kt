package williankl.bpProject.common.features.places.photoSelection

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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.components.ImagePager
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonConfig
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.models.IconConfig
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public data class PhotoSelectionScreen(
    private val imageUriList: List<String>
) : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val imageRetrievalController = LocalImageRetrievalController.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val runnerModel = rememberScreenModel<PhotoSelectionRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        var finalUriList by remember {
            mutableStateOf(imageUriList)
        }

        LaunchedEffect(finalUriList) {
            runnerModel.retrievePresentation(finalUriList)
        }

        PhotoSelectionContent(
            images = presentation.images,
            onDeleteRequested = { /* Nothing */ },
            onAddRequested = {
                imageRetrievalController.showBottomSheet(bottomSheetNavigator) { result ->
                    finalUriList = finalUriList + result
                }
            },
            onImagesConfirmed = { /* Nothing */ },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BeautifulColor.Background.composeColor,
                            BeautifulColor.Secondary.composeHoverColor,
                            BeautifulColor.Background.composeColor,
                        )
                    )
                )
                .fillMaxSize()
        )
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PhotoSelectionContent(
        images: List<ImageBitmap>,
        onDeleteRequested: () -> Unit,
        onAddRequested: () -> Unit,
        onImagesConfirmed: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current

        Column(
            verticalArrangement = Arrangement.spacedBy(26.dp),
            modifier = modifier,
        ) {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(30.dp)
            )

            ImagePager(
                images = images,
                contentPadding = PaddingValues(horizontal = 26.dp),
                pageSpacing = 28.dp,
                action = IconConfig(
                    painter = painterResource(SharedDesignCoreResources.images.ic_trash_close),
                    onClicked = onDeleteRequested,
                ),
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
                    config = ButtonConfig(
                        trailingIcon = IconConfig(
                            painter = painterResource(SharedDesignCoreResources.images.ic_chevron_right),
                        )
                    ),
                    modifier = Modifier,
                )
            }
        }
    }
}
