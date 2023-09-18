package williankl.bpProject.common.platform.stateHandler.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import williankl.bpProject.common.platform.stateHandler.LocalToolbarConfig
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.BeautifulToolbar
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public abstract class BeautifulScreen : Screen {

    public open val toolbarConfig: ToolbarConfig
        @Composable get() = remember { ToolbarConfig() }


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
