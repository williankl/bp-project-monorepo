package williankl.bpProject.common.platform.design.components.toolbar

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalBeautifulToolbarHandler: ProvidableCompositionLocal<ToolbarHandler> =
    staticCompositionLocalOf { ToolbarHandler() }