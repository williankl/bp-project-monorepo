package williankl.bpProject.common.features.dashboard.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.features.dashboard.pages.profile.UserProfileRunnerModel.UserProfilePresentation
import williankl.bpProject.common.features.dashboard.pages.profile.options.menu.MenuSidebarScreen
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.collectData
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination
import williankl.bpProject.common.platform.stateHandler.navigation.models.Places
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

internal object UserProfilePage : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() {
            val router = LocalRouter.currentOrThrow
            return remember {
                ToolbarConfig(
                    backgroundColor = BeautifulColor.Background,
                    trailingIcons = listOf(
                        ToolbarConfig.ToolbarAction(
                            icon = SharedDesignCoreResources.images.ic_heart,
                            onClick = { /* todo -> fix these */ }
                        ),
                        ToolbarConfig.ToolbarAction(
                            icon = SharedDesignCoreResources.images.ic_horizontal_ellipsis,
                            onClick = { router.showSidebar(MenuSidebarScreen) }
                        ),
                    ),
                )
            }
        }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<UserProfileRunnerModel>()
        val presentation by runnerModel.collectData()
        val router = LocalRouter.currentOrThrow

        UserProfileContent(
            presentation = presentation,
            onNextPageRequested = {
                if (runnerModel.hasLoadedAllPages.not()) {
                    runnerModel.loadNextPage()
                }
            },
            onPostSelected = { place ->
                router.push(
                    destination = Places.PlaceDetails(place)
                )
            },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize(),
        )
    }

    @Composable
    private fun UserProfileContent(
        presentation: UserProfilePresentation,
        onPostSelected: (Place) -> Unit,
        onNextPageRequested: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val state = rememberLazyStaggeredGridState()
        val shouldRequestNextPage by remember {
            derivedStateOf {
                state.firstVisibleItemIndex > presentation.posts.size || presentation.posts.isEmpty()
            }
        }

        LaunchedEffect(shouldRequestNextPage) {
            onNextPageRequested()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            UserInformationContent(
                presentation = presentation,
                modifier = Modifier,
            )

            LazyVerticalStaggeredGrid(
                state = state,
                contentPadding = PaddingValues(20.dp),
                verticalItemSpacing = 20.dp,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                columns = StaggeredGridCells.Fixed(2),
                content = {
                    items(presentation.posts) { post ->
                        post.images.firstOrNull()?.url
                            ?.let { imageUrl ->
                                AsyncImage(
                                    url = imageUrl,
                                    modifier = Modifier
                                        .clip(BeautifulShape.Rounded.Large.composeShape)
                                        .clickable { onPostSelected(post) }
                                )
                            }
                    }
                }
            )
        }
    }

    @Composable
    private fun UserInformationContent(
        presentation: UserProfilePresentation,
        modifier: Modifier = Modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier,
        ) {
            AsyncImage(
                url = presentation.avatarUrl,
                onError = {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_profile),
                        colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Circle.composeShape)
                    .background(BeautifulColor.Surface.composeColor)
                    .size(150.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = presentation.userFullName,
                    weight = FontWeight.SemiBold,
                    size = TextSize.Large,
                )

                if (presentation.userTag != null) {
                    Text(
                        text = presentation.userTag,
                        size = TextSize.Small,
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}
