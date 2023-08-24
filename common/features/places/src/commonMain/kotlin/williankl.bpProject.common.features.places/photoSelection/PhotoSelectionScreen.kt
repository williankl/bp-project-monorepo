package williankl.bpProject.common.features.places.photoSelection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonConfig
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.button.IconConfig
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

internal data class PhotoSelectionScreen(
    private val images: List<ImageBitmap>
) : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        PhotoSelectionContent(
            images = images,
            onDeleteRequested = { /* Nothing */ },
            onAddRequested = { /* Nothing */ },
            onImagesConfirmed = { /* Nothing */ },
            modifier = Modifier.fillMaxSize()
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
            modifier = modifier,
        ) {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier.weight(1f)
            ) {
                ImagePager(
                    images = images,
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(SharedDesignCoreResources.images.ic_trash_close),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(24.dp)
                        .clip(BeautifulShape.Rounded.Circle.composeShape)
                        .clickable { onDeleteRequested() }
                        .background(
                            color = BeautifulColor.Black.composeHoverColor,
                            shape = BeautifulShape.Rounded.Circle.composeShape,
                        )
                        .padding(6.dp)
                        .size(30.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier,
        ) {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_camera_plus),
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

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ImagePager(
    images: List<ImageBitmap>,
    state: PagerState = rememberPagerState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        HorizontalPager(
            state = state,
            contentPadding = contentPadding,
            pageSize = pageSize,
            beyondBoundsPageCount = beyondBoundsPageCount,
            pageSpacing = pageSpacing,
            verticalAlignment = verticalAlignment,
            userScrollEnabled = userScrollEnabled,
            pageCount = images.size,
            modifier = Modifier.clip(BeautifulShape.Rounded.Regular.composeShape),
        ) { page ->
            val image = images[page]
            Image(
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        AnimatedVisibility(
            visible = images.size > 1,
            content = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 6.dp),
                ) {
                    repeat(images.size) { index ->
                        Spacer(
                            modifier = Modifier
                                .background(
                                    shape = BeautifulShape.Rounded.Circle.composeShape,
                                    color = BeautifulColor.Secondary.composeColor(state.currentPage != index),
                                )
                                .size(4.dp)
                        )
                    }
                }
            }
        )
    }
}
