package williankl.bpProject.common.platform.design.core.colors

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

private const val HoverAlpha = 0.5f

public val BeautifulPainter.hover: BeautifulPainter
    @Composable get() = when (this) {
        is BeautifulColor -> hover
        is BeautifulBrush -> {
            BeautifulBrush.Custom(
                colors = this.colors.map { color -> color.hover }.toTypedArray()
            )
        }
    }

public val BeautifulColor.composeColor: Color
    @Composable get() {
        val localColorTheme = LocalColorTheme.current

        val currentColor =
            if (localColorTheme == ColorTheme.Light) {
                lightColor
            } else {
                darkColor
            }

        val animatedColor by animateColorAsState(currentColor)
        return animatedColor
    }

public val BeautifulColor.composeHoverColor: Color
    @Composable get() = composeColor.copy(alpha = HoverAlpha)

public val BeautifulColor.hover: BeautifulColor
    @Composable get() = BeautifulColor.Custom(
        lightColor = composeHoverColor,
        darkColor = composeHoverColor,
    )

@Composable
public fun BeautifulColor.composeColor(isHover: Boolean): Color =
    if (isHover) composeHoverColor else composeColor

@Composable
public fun BeautifulColor.color(isHover: Boolean = false): BeautifulColor =
    if (isHover) hover else this

@Composable
public fun BeautifulColor.alpha(alpha: Float): BeautifulColor =
    BeautifulColor.Custom(
        lightColor = lightColor.copy(alpha = alpha),
        darkColor = darkColor.copy(alpha = alpha),
    )

@Composable
public fun BeautifulColor.composeAlpha(alpha: Float): Color =
    alpha(alpha).composeColor

public fun BeautifulColor.argb(dark: Boolean = true): Int {
    return when (dark) {
        true -> darkColor.toArgb()
        false -> lightColor.toArgb()
    }
}

public fun BeautifulColor.nonComposableColor(dark: Boolean = true): Color {
    return when (dark) {
        true -> darkColor
        false -> lightColor
    }
}

public sealed class BeautifulColor(
    internal val lightColor: Color,
    internal val darkColor: Color,
) : BeautifulPainter {

    public data object PrimaryHigh : BeautifulColor(
        lightColor = Color(0xffAD4084),
        darkColor = Color(0xffAD4084),
    )

    public data object Primary : BeautifulColor(
        lightColor = Color(0xff932F6D),
        darkColor = Color(0xff932F6D),
    )

    public data object PrimaryLow : BeautifulColor(
        lightColor = Color(0xff7A1B56),
        darkColor = Color(0xff7A1B56),
    )

    public data object SecondaryHigh : BeautifulColor(
        lightColor = Color(0xffCCD9C5),
        darkColor = Color(0xffCCD9C5),
    )

    public data object Secondary : BeautifulColor(
        lightColor = Color(0xffBFD0B5),
        darkColor = Color(0xffBFD0B5),
    )

    public data object SecondaryLow : BeautifulColor(
        lightColor = Color(0xff9DB88D),
        darkColor = Color(0xff9DB88D),
    )

    public data object DangerHigh : BeautifulColor(
        lightColor = Color(0xffC7384E),
        darkColor = Color(0xffC7384E),
    )

    public data object Danger : BeautifulColor(
        lightColor = Color(0xffAE1F35),
        darkColor = Color(0xffAE1F35),
    )

    public data object DangerLow : BeautifulColor(
        lightColor = Color(0xff941327),
        darkColor = Color(0xff941327),
    )

    public data object SuccessHigh : BeautifulColor(
        lightColor = Color(0xff67AB83),
        darkColor = Color(0xff67AB83),
    )

    public data object Success : BeautifulColor(
        lightColor = Color(0xff499167),
        darkColor = Color(0xff499167),
    )

    public data object SuccessLow : BeautifulColor(
        lightColor = Color(0xff30784E),
        darkColor = Color(0xff30784E),
    )

    public data object NeutralHigh : BeautifulColor(
        lightColor = Color.White,
        darkColor = Color.White,
    )

    public data object Neutral : BeautifulColor(
        lightColor = Color.White,
        darkColor = Color.White,
    )

    public data object NeutralLow : BeautifulColor(
        lightColor = Color.White,
        darkColor = Color.White,
    )

    public data object Surface : BeautifulColor(
        lightColor = Color(0xff4F4F4F),
        darkColor = Color(0xff4F4F4F),
    )

    public data object Background : BeautifulColor(
        lightColor = Color(0xff212121),
        darkColor = Color(0xff212121),
    )

    public data object BackgroundHigh : BeautifulColor(
        lightColor = Color(0xff141115),
        darkColor = Color(0xff141115),
    )

    public data object Border : BeautifulColor(
        lightColor = Color(0xff353535),
        darkColor = Color(0xff353535),
    )

    public data object Transparent : BeautifulColor(
        lightColor = Color.Transparent,
        darkColor = Color.Transparent,
    )

    public data object White : BeautifulColor(
        lightColor = Color.White,
        darkColor = Color.White,
    )

    public data object Black : BeautifulColor(
        lightColor = Color.Black,
        darkColor = Color.Black,
    )

    public class Custom(
        lightColor: Color,
        darkColor: Color,
    ) : BeautifulColor(
        lightColor = lightColor,
        darkColor = darkColor,
    )
}
