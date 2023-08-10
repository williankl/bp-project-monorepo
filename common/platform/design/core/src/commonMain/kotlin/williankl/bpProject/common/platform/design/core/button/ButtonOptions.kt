package williankl.bpProject.common.platform.design.core.button

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.text.TextSize

public enum class ButtonType(
    internal val textSize: TextSize,
    internal val iconSize: Dp,
) {
    Small(
        textSize = TextSize.XSmall,
        iconSize = 10.dp,
    ),

    Regular(
        textSize = TextSize.Regular,
        iconSize = 16.dp,
    ),

    Large(
        textSize = TextSize.XLarge,
        iconSize = 20.dp,
    ),
}

public enum class ButtonVariant(
    internal val backgroundColor: BeautifulPainter,
    internal val borderColor: BeautifulPainter,
    internal val tint: BeautifulColor,
) {
    Primary(
        backgroundColor = BeautifulColor.PrimaryHigh,
        borderColor = BeautifulColor.Transparent,
        tint = BeautifulColor.NeutralHigh,
    ),

    Secondary(
        backgroundColor = BeautifulColor.Transparent,
        borderColor = BeautifulColor.PrimaryHigh,
        tint = BeautifulColor.PrimaryHigh,
    ),

    Disabled(
        backgroundColor = BeautifulColor.Transparent,
        borderColor = BeautifulColor.PrimaryHigh,
        tint = BeautifulColor.PrimaryHigh,
    ),
}

public data class ButtonConfig(
    val headingIcon: IconConfig? = null,
    val trailingIcon: IconConfig? = null,
)

public data class IconConfig(
    val painter: Painter,
    val description: String? = null,
)
