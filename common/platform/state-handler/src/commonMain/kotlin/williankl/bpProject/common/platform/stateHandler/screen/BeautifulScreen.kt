package williankl.bpProject.common.platform.stateHandler.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.stateHandler.LocalToolbarConfig
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.BeautifulToolbar
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public abstract class BeautifulScreen : Screen {

    public open val toolbarConfig: ToolbarConfig
        @Composable get() {
            val navigator = LocalNavigator.currentOrThrow
            return remember {
                ToolbarConfig(
                    headingIcon =
                    if (navigator.canPop) {
                        ToolbarConfig.ToolbarAction(
                            icon = SharedDesignCoreResources.images.ic_chevron_left,
                            onClick = { navigator.pop() }
                        )
                    } else null
                )
            }
        }

    @Composable
    override fun Content() {
        val hasToolbarContent = toolbarConfig.label != null ||
            toolbarConfig.headingIcon != null ||
            toolbarConfig.trailingIcons.isNotEmpty()

        Column {
            AnimatedVisibility(
                visible = hasToolbarContent,
            ) {
                BeautifulToolbar(
                    label = toolbarConfig.label,
                    headingIcon = toolbarConfig.headingIcon,
                    backgroundColor = toolbarConfig.backgroundColor,
                    trailingIcons = toolbarConfig.trailingIcons,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            CompositionLocalProvider(
                LocalToolbarConfig provides toolbarConfig
            ) {
                BeautifulContent()
            }
        }
    }

    @Composable
    public abstract fun BeautifulContent()
}
