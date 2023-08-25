package williankl.bpProject.common.platform.design.core.button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.models.IconConfig
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.TextSize

public enum class ButtonType(
    internal val textSize: TextSize,
    internal val iconSize: Dp,
    internal val verticalPadding: Dp,
    internal val horizontalPadding: Dp,
    internal val shape: BeautifulShape,
) {
    Pill(
        textSize = TextSize.Regular,
        iconSize = 20.dp,
        verticalPadding = 2.dp,
        horizontalPadding = 10.dp,
        shape = BeautifulShape.Rounded.Regular
    ),

    Regular(
        textSize = TextSize.Regular,
        iconSize = 16.dp,
        verticalPadding = 10.dp,
        horizontalPadding = 10.dp,
        shape = BeautifulShape.Rounded.Regular,
    ),
}

public enum class ButtonVariant(
    internal val backgroundColor: BeautifulPainter,
    internal val borderColor: BeautifulPainter,
    internal val tint: BeautifulColor,
) {
    Primary(
        backgroundColor = BeautifulColor.Primary,
        borderColor = BeautifulColor.Transparent,
        tint = BeautifulColor.SecondaryHigh,
    ),

    PrimaryGhost(
        backgroundColor = BeautifulColor.Transparent,
        borderColor = BeautifulColor.Primary,
        tint = BeautifulColor.SecondaryHigh,
    ),

    Secondary(
        backgroundColor = BeautifulColor.Secondary,
        borderColor = BeautifulColor.Transparent,
        tint = BeautifulColor.PrimaryHigh,
    ),

    SecondaryGhost(
        backgroundColor = BeautifulColor.Transparent,
        borderColor = BeautifulColor.Secondary,
        tint = BeautifulColor.PrimaryHigh,
    ),
}

public data class ButtonConfig(
    val headingIcon: IconConfig? = null,
    val trailingIcon: IconConfig? = null,
)
