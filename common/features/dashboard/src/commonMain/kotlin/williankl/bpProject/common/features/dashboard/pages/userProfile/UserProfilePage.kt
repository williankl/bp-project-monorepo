package williankl.bpProject.common.features.dashboard.pages.userProfile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.dashboard.pages.userProfile.UserProfileRunnerModel.UserProfilePresentation
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal object UserProfilePage : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<UserProfileRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        UserProfileContent(
            presentation = presentation,
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize(),
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun UserProfileContent(
        presentation: UserProfilePresentation,
        modifier: Modifier = Modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            UserInformationContent(
                presentation = presentation,
                modifier = Modifier,
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                content = {
                    items(presentation.posts) { post ->
                        post.imageUrls.firstOrNull()
                            ?.let { imageUrl ->
                                AsyncImage(
                                    url = imageUrl,
                                    modifier = Modifier.clip(BeautifulShape.Rounded.Large.composeShape)
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

                Text(
                    text = presentation.userTag,
                    size = TextSize.Small,
                )
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
