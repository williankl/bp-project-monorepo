package williankl.bpProject.common.application

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.bpProject.common.application.internal.RouterInfrastructure
import williankl.bpProject.common.data.imageRetrievalService.controller.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import williankl.bpProject.common.platform.stateHandler.LocalRouter

@Composable
@OptIn(ExperimentalAnimationApi::class)
public fun AppContent(
    imageRetrievalController: ImageRetrievalController,
) {
    val router = remember {
        RouterInfrastructure()
    }
    BeautifulThemeContent {
        CompositionLocalProvider(
            LocalImageRetrievalController provides imageRetrievalController,
            LocalRouter provides router,
        ) {
            WithNavigators { navigator, bottomSheetNavigator ->
                LaunchedEffect(navigator, bottomSheetNavigator) {
                    router.mutableNavigator = navigator
                    router.mutableBottomSheetNavigator = bottomSheetNavigator
                }

                val blurDp =
                    if (router.isSidebarVisible || router.isBottomSheetVisible) 12.dp
                    else 0.dp

                val animatedBlurDp by animateDpAsState(
                    label = "content-blur-dp",
                    targetValue = blurDp
                )

                Box(
                    modifier = Modifier
                        .blur(animatedBlurDp)
                        .fillMaxSize(),
                    content = {
                        SlideTransition(navigator)
                        HandleSideBar(router)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WithNavigators(
    content: @Composable (Navigator, BottomSheetNavigator) -> Unit,
) {
    BottomSheetNavigator(
        scrimColor = BeautifulColor.Black.composeHoverColor,
        sheetBackgroundColor = BeautifulColor.Background.composeColor,
        sheetShape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        ),
    ) { bottomSheetNavigator ->
        Navigator(
            screen = DashboardScreen(),
            onBackPressed = { true },
            content = { navigator -> content(navigator, bottomSheetNavigator) },
        )
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun BoxScope.HandleSideBar(
    routerInfrastructure: RouterInfrastructure
) {
    AnimatedContent(
        targetState = routerInfrastructure.mutableSideBarContent,
        transitionSpec = { expandHorizontally() with shrinkHorizontally() },
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .fillMaxHeight(),
    ) { content ->
        content?.invoke()
    }
}
