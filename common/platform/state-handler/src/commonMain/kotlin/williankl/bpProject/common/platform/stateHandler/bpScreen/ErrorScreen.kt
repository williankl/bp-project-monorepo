package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.nonClickable

@Composable
public fun ErrorScreen(
    reason: Throwable,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        println(reason.message)
        reason.printStackTrace()
    }

    Box(
        modifier = modifier
            .background(BeautifulColor.DangerLow.composeColor)
            .nonClickable()
    )
}
