package williankl.bpProject.common.platform.stateHandler

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import williankl.bpProject.common.platform.stateHandler.navigation.Router
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public val LocalRouter: ProvidableCompositionLocal<Router?> = staticCompositionLocalOf { null }

public val LocalToolbarConfig: ProvidableCompositionLocal<ToolbarConfig> = staticCompositionLocalOf { ToolbarConfig() }
