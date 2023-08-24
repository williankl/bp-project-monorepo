package williankl.bpProject.common.platform.design.core.button

import androidx.compose.ui.graphics.painter.Painter
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
        textSize = TextSize.XSmall,
        iconSize = 6.dp,
        verticalPadding = 6.dp,
        horizontalPadding = 6.dp,
        shape = BeautifulShape.Rounded.Regular
    ),

    Small(
        textSize = TextSize.XSmall,
        iconSize = 10.dp,
        verticalPadding = 8.dp,
        horizontalPadding = 8.dp,
        shape = BeautifulShape.Rounded.Regular,
    ),

    Regular(
        textSize = TextSize.Regular,
        iconSize = 16.dp,
        verticalPadding = 10.dp,
        horizontalPadding = 10.dp,
        shape = BeautifulShape.Rounded.Regular,
    ),

    Large(
        textSize = TextSize.XLarge,
        iconSize = 20.dp,
        verticalPadding = 12.dp,
        horizontalPadding = 12.dp,
        shape = BeautifulShape.Rounded.Regular,
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
        tint = BeautifulColor.NeutralHigh,
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


