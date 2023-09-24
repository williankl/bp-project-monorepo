package williankl.bpProject.common.platform.design.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import korlibs.io.async.launch
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape

@Composable
@OptIn(ExperimentalFoundationApi::class)
public fun AsyncImagePager(
    urls: List<String>,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(
        pageCount = { urls.size },
    ),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
    bottomContent: @Composable () -> Unit = {
        DefaultBulletPageCount(
            count = state.pageCount,
            currentSelectedIndex = state.currentPage,
            modifier = Modifier.padding(top = 6.dp),
        )
    },
) {
    CorePager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        pageSize = pageSize,
        beyondBoundsPageCount = beyondBoundsPageCount,
        pageSpacing = pageSpacing,
        verticalAlignment = verticalAlignment,
        userScrollEnabled = userScrollEnabled,
        bottomContent = bottomContent,
        content = { page ->
            AsyncImage(
                url = urls[page],
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Large.composeShape)
                    .fillMaxSize()
            )
        }
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
public fun ImagePager(
    images: List<ImageBitmap>,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(
        pageCount = { images.size },
    ),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
    bottomContent: @Composable () -> Unit = {
        DefaultBulletPageCount(
            count = state.pageCount,
            currentSelectedIndex = state.currentPage,
            modifier = Modifier.padding(top = 6.dp),
        )
    },
) {
    CorePager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        pageSize = pageSize,
        beyondBoundsPageCount = beyondBoundsPageCount,
        pageSpacing = pageSpacing,
        verticalAlignment = verticalAlignment,
        userScrollEnabled = userScrollEnabled,
        bottomContent = bottomContent,
        content = { page ->
            Image(
                bitmap = images[page],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Large.composeShape)
                    .fillMaxSize()
            )
        }
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
public fun <T> ActionedImagePager(
    imageMap: Map<T, ImageBitmap>,
    actionResource: ImageResource,
    onActionClicked: (T) -> Unit,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(
        pageCount = { imageMap.size }
    ),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
) {
    val coroutineScope = rememberCoroutineScope()
    val pairList = imageMap.map { (key, image) ->
        key to image
    }

    ImagePager(
        state = state,
        contentPadding = contentPadding,
        pageSize = pageSize,
        beyondBoundsPageCount = beyondBoundsPageCount,
        pageSpacing = pageSpacing,
        verticalAlignment = verticalAlignment,
        userScrollEnabled = userScrollEnabled,
        modifier = modifier,
        images = pairList.map { pairListItem -> pairListItem.second },
        bottomContent = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 6.dp),
            ) {
                repeat(pairList.size) { index ->
                    val isPagerInCurrentPhoto = index == state.currentPage

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(BeautifulShape.Rounded.Circle.composeShape)
                            .size(30.dp)
                            .border(
                                shape = BeautifulShape.Rounded.Circle.composeShape,
                                width = 1.dp,
                                color =
                                if (isPagerInCurrentPhoto) BeautifulColor.NeutralHigh.composeColor
                                else BeautifulColor.Transparent.composeColor,
                            )
                            .clickable {
                                if (index == state.currentPage) {
                                    onActionClicked(pairList[index].first)
                                } else {
                                    coroutineScope.launch {
                                        state.animateScrollToPage(index)
                                    }
                                }
                            }
                    ) {
                        Image(
                            bitmap = pairList[index].second,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )

                        AnimatedContent(
                            targetState = index == state.currentPage,
                            transitionSpec = { fadeIn() with fadeOut() }
                        ) { isCurrentPage ->
                            if (isCurrentPage) {
                                Spacer(
                                    modifier = Modifier
                                        .background(
                                            shape = BeautifulShape.Rounded.Circle.composeShape,
                                            color = BeautifulColor.Neutral.composeHoverColor,
                                        )
                                )

                                Image(
                                    painter = painterResource(actionResource),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                                    modifier = Modifier.padding(4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CorePager(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    userScrollEnabled: Boolean = true,
    bottomContent: @Composable () -> Unit,
    content: @Composable (page: Int) -> Unit,
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
            modifier = Modifier.weight(1f),
            pageContent = { page -> content(page) },
        )

        AnimatedVisibility(
            label = "bullet-visibility-animation",
            visible = state.pageCount > 1,
            content = {
                bottomContent()
            }
        )
    }
}

@Composable
private fun DefaultBulletPageCount(
    count: Int,
    currentSelectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier,
    ) {
        repeat(count) { index ->
            Spacer(
                modifier = Modifier
                    .background(
                        shape = BeautifulShape.Rounded.Circle.composeShape,
                        color = BeautifulColor.Secondary.composeColor(currentSelectedIndex != index),
                    )
                    .size(6.dp)
            )
        }
    }
}
