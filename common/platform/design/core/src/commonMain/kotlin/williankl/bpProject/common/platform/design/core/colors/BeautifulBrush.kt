package williankl.bpProject.common.platform.design.core.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
public fun BeautifulBrush.linearGradient(isHover: Boolean = false): Brush {
    return Brush.linearGradient(
        colors = colors.map { color ->
            if (isHover) color.composeHoverColor else color.composeColor
        },
    )
}

@Composable
public fun BeautifulBrush.radialGradient(isHover: Boolean = false): Brush {
    return Brush.radialGradient(
        colors = colors.map { color ->
            if (isHover) color.composeHoverColor else color.composeColor
        },
    )
}

public sealed class BeautifulBrush(
    public vararg val colors: BeautifulColor,
) : BeautifulPainter {
    public data object Brand : BeautifulBrush(BeautifulColor.PrimaryHigh, BeautifulColor.SecondaryHigh)

    public class Custom(vararg colors: BeautifulColor) : BeautifulBrush(*colors)
}
