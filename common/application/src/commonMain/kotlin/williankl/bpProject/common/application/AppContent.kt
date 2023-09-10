package williankl.bpProject.common.application

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
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

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
public fun AppContent(
    imageRetrievalController: ImageRetrievalController,
    toolbarHandler: ToolbarHandler = ToolbarHandler(),
) {
    BeautifulThemeContent {
        CompositionLocalProvider(
            LocalImageRetrievalController provides imageRetrievalController,
            LocalBeautifulToolbarHandler provides toolbarHandler,
        ) {
            BottomSheetNavigator(
                scrimColor = BeautifulColor.Black.composeHoverColor,
                sheetBackgroundColor = BeautifulColor.Background.composeColor,
                sheetShape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                ),
            ) { bottomSheetNav ->
                val blurDp = if (bottomSheetNav.isVisible) 12.dp else 0.dp
                val animatedBlurDp by animateDpAsState(
                    label = "content-blur-dp",
                    targetValue = blurDp
                )

                Navigator(
                    screen = DashboardScreen,
                    onBackPressed = { true }
                ) { nav ->
                    Column(
                        modifier = Modifier.blur(animatedBlurDp)
                    ) {
                        AnimatedVisibility(
                            visible = toolbarHandler.visible,
                        ) {
                            BeautifulToolbar(
                                label = toolbarHandler.label,
                                headingIcon = toolbarHandler.headingIcon,
                                backgroundColor = toolbarHandler.backgroundColor,
                                trailingIcons = toolbarHandler.trailingIcons,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        SlideTransition(nav)
                    }
                }
            }
        }
    }
}