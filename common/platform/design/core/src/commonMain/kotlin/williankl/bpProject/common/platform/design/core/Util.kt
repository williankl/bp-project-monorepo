package williankl.bpProject.common.platform.design.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.colors.ColorTheme
import williankl.bpProject.common.platform.design.core.colors.LocalColorTheme
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape

public typealias ComposeString = @Composable () -> String

internal fun retrieveBPFontFamily(): FontFamily = FontFamily.Monospace

public fun Modifier.overlappingHeight(size: Dp): Modifier = layout { measurable, constraints ->
    val newHeight = constraints.maxHeight + size.toPx()
    val placeable = measurable.measure(
        constraints.copy(maxHeight = newHeight.toInt())
    )
    layout(placeable.width, placeable.height) {
        placeable.place(0, 0)
    }
}

public fun Modifier.nonClickable(): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = false,
        onClick = { /* Nothing */ }
    )
}

public fun Modifier.modifyIf(
    condition: Boolean,
    builder: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) builder() else this
}

public fun Modifier.clickableIcon(
    padding: Dp = 6.dp,
    onClick: () -> Unit,
): Modifier {
    return clip(BeautifulShape.Rounded.Regular.composeShape)
        .clickable { onClick() }
        .padding(padding)
}

@Composable
public fun IntSize.toDpSize(): DpSize {
    val density = LocalDensity.current
    return with(density) {
        DpSize(
            width = this@toDpSize.width.toDp(),
            height = this@toDpSize.height.toDp(),
        )
    }
}

@Composable
public fun themedLogoResource(): ImageResource {
    val theme = LocalColorTheme.current
    return when (theme) {
        ColorTheme.Dark -> SharedDesignCoreResources.images.bp_logo_dark
        ColorTheme.Light -> SharedDesignCoreResources.images.bp_logo_light
    }
}
