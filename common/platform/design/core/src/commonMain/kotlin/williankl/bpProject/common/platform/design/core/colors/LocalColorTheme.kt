package williankl.bpProject.common.platform.design.core.colors

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

public enum class ColorTheme {
    Dark, Light
}

public val LocalColorTheme: ProvidableCompositionLocal<ColorTheme> =
    compositionLocalOf { ColorTheme.Light }

public class ThemeHandler {
    public var currentTheme: ColorTheme by mutableStateOf(ColorTheme.Light)
}
