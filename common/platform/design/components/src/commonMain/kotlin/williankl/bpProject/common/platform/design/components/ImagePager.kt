package williankl.bpProject.common.platform.design.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.models.IconConfig
import williankl.bpProject.common.platform.design.core.modifyIf
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
public fun ImagePager(
    images: List<ImageBitmap>,
    state: PagerState = rememberPagerState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
    action: IconConfig? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
            modifier = Modifier.weight(1f),
        ) { page ->
            val image = images[page]

            Box {
                Image(
                    bitmap = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(BeautifulShape.Rounded.Regular.composeShape)
                        .fillMaxSize()
                )

                AnimatedContent(
                    targetState = action != null
                ) { hasAction ->
                    if (hasAction && action != null) {
                        Image(
                            painter = action.painter,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(BeautifulColor.Secondary.composeColor),
                            modifier = Modifier
                                .padding(24.dp)
                                .clip(BeautifulShape.Rounded.Circle.composeShape)
                                .modifyIf(action.onClicked != null) {
                                    clickable { action.onClicked?.invoke() }
                                }
                                .background(
                                    color = BeautifulColor.Black.composeHoverColor,
                                    shape = BeautifulShape.Rounded.Circle.composeShape,
                                )
                                .padding(6.dp)
                                .size(30.dp)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            label = "bullet-visibility-animation",
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
                                .size(6.dp)
                        )
                    }
                }
            }
        )
    }
}
