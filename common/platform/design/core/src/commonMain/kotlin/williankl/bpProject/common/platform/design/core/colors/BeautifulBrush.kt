package williankl.bpProject.common.platform.design.core.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
public fun BeautifulBrush.linearGradient(isHover: Boolean = false): Brush {
    return Brush.linearGradient(
        colors = colors.map { proColor ->
            if (isHover) proColor.composeHoverColor else proColor.composeColor
        },
    )
}

@Composable
public fun BeautifulBrush.radialGradient(isHover: Boolean = false): Brush {
    return Brush.radialGradient(
        colors = colors.map { proColor ->
            if (isHover) proColor.composeHoverColor else proColor.composeColor
        },
    )
}

public sealed class BeautifulBrush(
    public vararg val colors: BeautifulColor,
) : BeautifulPainter {
    public data object Brand : BeautifulBrush(BeautifulColor.BrandPrimary, BeautifulColor.BrandSecondary)

    public class Custom(vararg colors: BeautifulColor) : BeautifulBrush(*colors)
}
