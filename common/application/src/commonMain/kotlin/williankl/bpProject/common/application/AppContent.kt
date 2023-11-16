package williankl.bpProject.common.application

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.kodein.di.compose.localDI
import williankl.bpProject.common.application.internal.RouterInfrastructure
import williankl.bpProject.common.application.internal.attachClientBearerToken
import williankl.bpProject.common.data.imageRetrievalService.controller.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.navigation.Router

@Composable
public fun AppContent() {
    val localDi = localDI()

    LaunchedEffect(Unit) {
        with(localDi) {
            attachClientBearerToken()
        }
    }

    val router = remember {
        RouterInfrastructure()
    }

    BeautifulThemeContent {
        CompositionLocalProvider(
            LocalImageRetrievalController provides ImageRetrievalController(),
            LocalRouter provides router,
        ) {
            WithNavigators(router) { navigator, bottomSheetNavigator ->
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
    router: Router,
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
            onBackPressed = { _ ->
                when {
                    router.isSidebarVisible -> {
                        router.hideSidebar()
                        false
                    }
                    else -> true
                }
            },
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
        targetState = routerInfrastructure.isSidebarVisible,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        modifier = Modifier.fillMaxSize(),
    ) { showScrim ->
        Spacer(
            modifier = Modifier.fillMaxSize()
        )

        if (showScrim) {
            Spacer(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { routerInfrastructure.hideSidebar() }
                    )
                    .background(BeautifulColor.Black.composeHoverColor)
                    .fillMaxSize()
            )
        }
    }

    AnimatedContent(
        targetState = routerInfrastructure.mutableSideBarContent,
        transitionSpec = {
            expandHorizontally(expandFrom = Alignment.End) with
                shrinkHorizontally(shrinkTowards = Alignment.End)
        },
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .fillMaxHeight(),
    ) { content ->
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                    )
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* Prevent clicking */ }
                )
        ) {
            content?.invoke()
        }
    }
}
