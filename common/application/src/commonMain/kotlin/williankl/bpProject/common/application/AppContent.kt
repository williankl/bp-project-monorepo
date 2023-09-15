package williankl.bpProject.common.application

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import williankl.bpProject.common.platform.design.components.toolbar.BeautifulToolbar
import williankl.bpProject.common.platform.design.components.toolbar.LocalBeautifulToolbarHandler
import williankl.bpProject.common.platform.design.components.toolbar.ToolbarHandler
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import williankl.bpProject.common.platform.stateHandler.navigation.LocalRouter

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
public fun AppContent(
    imageRetrievalController: ImageRetrievalController,
    toolbarHandler: ToolbarHandler,
) {
    val router = remember {
        RouterInfrastructure()
    }

    BeautifulThemeContent {
        CompositionLocalProvider(
            LocalImageRetrievalController provides imageRetrievalController,
            LocalBeautifulToolbarHandler provides toolbarHandler,
            LocalRouter provides router,
        ) {
            BottomSheetNavigator(
                scrimColor = BeautifulColor.Black.composeHoverColor,
                sheetBackgroundColor = BeautifulColor.Background.composeColor,
                sheetShape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                ),
            ) { bottomSheetNavigator ->
                val blurDp = if (bottomSheetNavigator.isVisible) 12.dp else 0.dp
                val animatedBlurDp by animateDpAsState(
                    label = "content-blur-dp",
                    targetValue = blurDp
                )
                Navigator(
                    screen = DashboardScreen,
                    onBackPressed = { true }
                ) { navigator ->
                    LaunchedEffect(navigator, bottomSheetNavigator) {
                        router.mutableNavigator = navigator
                        router.mutableBottomSheetNavigator = bottomSheetNavigator
                    }

                    Column(
                        modifier = Modifier.blur(animatedBlurDp)
                    ) {
                        HandleToolbarContent(toolbarHandler)
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.HandleToolbarContent(
    toolbarHandler: ToolbarHandler,
) {
    val hasToolbarContent = toolbarHandler.label != null ||
        toolbarHandler.headingIcon != null ||
        toolbarHandler.trailingIcons.isNotEmpty()

    AnimatedVisibility(
        visible = toolbarHandler.visible && hasToolbarContent,
    ) {
        BeautifulToolbar(
            label = toolbarHandler.label,
            headingIcon = toolbarHandler.headingIcon,
            backgroundColor = toolbarHandler.backgroundColor,
            trailingIcons = toolbarHandler.trailingIcons,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
