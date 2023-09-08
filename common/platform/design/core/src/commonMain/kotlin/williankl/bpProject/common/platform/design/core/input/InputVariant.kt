package williankl.bpProject.common.platform.design.core.input

import williankl.bpProject.common.platform.design.core.colors.BeautifulColor

public enum class InputVariant(
    internal val borderColor: BeautifulColor,
    internal val borderColorFocused: BeautifulColor,
    internal val backgroundColor: BeautifulColor,
) {
    Default(
        borderColor = BeautifulColor.Transparent,
        borderColorFocused = BeautifulColor.Transparent,
        backgroundColor = BeautifulColor.Surface,
    ),

    Primary(
        borderColor = BeautifulColor.Surface,
        borderColorFocused = BeautifulColor.PrimaryHigh,
        backgroundColor = BeautifulColor.Transparent,
    ),
}
