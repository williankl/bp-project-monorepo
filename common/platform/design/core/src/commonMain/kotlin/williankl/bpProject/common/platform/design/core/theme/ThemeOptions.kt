package williankl.bpProject.common.platform.design.core.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.ColorTheme
import williankl.bpProject.common.platform.design.core.colors.LocalColorTheme
import williankl.bpProject.common.platform.design.core.colors.composeColor

@Composable
public fun BeautifulThemeContent(
    withTheme: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = BeautifulColors,
    ) {
        withTheme()
    }
}

internal val BeautifulColors: Colors
    @Composable get() {
        val colorTheme = LocalColorTheme.current
        return Colors(
            primary = BeautifulColor.Primary.composeColor,
            primaryVariant = BeautifulColor.PrimaryLow.composeColor,
            secondary = BeautifulColor.Secondary.composeColor,
            secondaryVariant = BeautifulColor.SecondaryLow.composeColor,
            background = BeautifulColor.Background.composeColor,
            surface = BeautifulColor.Surface.composeColor,
            error = BeautifulColor.Danger.composeColor,
            onPrimary = BeautifulColor.SecondaryHigh.composeColor,
            onSecondary = BeautifulColor.PrimaryHigh.composeColor,
            onBackground = BeautifulColor.White.composeColor,
            onSurface = BeautifulColor.White.composeColor,
            onError = BeautifulColor.White.composeColor,
            isLight = colorTheme == ColorTheme.Light,
        )
    }
