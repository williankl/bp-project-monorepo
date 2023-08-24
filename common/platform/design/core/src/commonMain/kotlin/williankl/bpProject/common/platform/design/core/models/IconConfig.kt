package williankl.bpProject.common.platform.design.core.models

import androidx.compose.ui.graphics.painter.Painter

public data class IconConfig(
    val painter: Painter,
    val onClicked: (() -> Unit)? = null,
    val description: String? = null,
)