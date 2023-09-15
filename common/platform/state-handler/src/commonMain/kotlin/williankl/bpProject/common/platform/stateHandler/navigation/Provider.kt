package williankl.bpProject.common.platform.stateHandler.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalRouter: ProvidableCompositionLocal<Router?> = staticCompositionLocalOf { null }