package williankl.bpProject.common.features.places

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor

@Composable
internal fun Divider() {
    Spacer(
        modifier = Modifier
            .background(BeautifulColor.Border.composeColor)
            .height(1.dp)
            .fillMaxWidth()
    )
}