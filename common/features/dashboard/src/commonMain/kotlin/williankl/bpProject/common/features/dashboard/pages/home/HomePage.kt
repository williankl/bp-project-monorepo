package williankl.bpProject.common.features.dashboard.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import williankl.bpProject.common.features.dashboard.LocalDashboardStrings
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

internal object HomePage : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() {
            val strings = LocalDashboardStrings.current
            return remember {
                ToolbarConfig(
                    label = strings.projectName,
                    backgroundColor = BeautifulColor.BackgroundHigh,
                )
            }
        }
    @Composable
    override fun BeautifulContent() {
        Box(Modifier.fillMaxSize())
    }
}
