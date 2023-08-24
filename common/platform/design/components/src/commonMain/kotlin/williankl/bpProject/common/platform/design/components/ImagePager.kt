package williankl.bpProject.common.platform.design.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape

@Composable
@OptIn(ExperimentalFoundationApi::class)
public fun ImagePager(
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