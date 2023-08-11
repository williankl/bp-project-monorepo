package williankl.bpProject.common.platform.design.core.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulBrush
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.linearGradient
import williankl.bpProject.common.platform.design.core.modifyIf
import williankl.bpProject.common.platform.design.core.text.Text

@Composable
public fun IconButton(
    iconConfig: IconConfig,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: ButtonType = ButtonType.Regular,
    variant: ButtonVariant = ButtonVariant.Primary,
) {
    val actualVariant =
        if (enabled) variant
        else ButtonVariant.Disabled

    CoreButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        variant = actualVariant,
        shape = type.shape.composeShape,
        content = {
            Icon(
                painter = iconConfig.painter,
                contentDescription = iconConfig.description,
                tint = actualVariant.tint.composeColor,
                modifier = Modifier.size(type.iconSize),
            )
        },
    )
}

@Composable
public fun Button(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconsOnExtremes: Boolean = false,
    type: ButtonType = ButtonType.Regular,
    config: ButtonConfig = ButtonConfig(),
    variant: ButtonVariant = ButtonVariant.Primary,
) {
    val actualVariant =
        if (enabled) variant
        else ButtonVariant.Disabled

    CoreButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        variant = actualVariant,
        shape = type.shape.composeShape,
        content = {
            if (config.headingIcon != null) {
                Icon(
                    painter = config.headingIcon.painter,
                    contentDescription = config.headingIcon.description,
                    tint = actualVariant.tint.composeColor,
                    modifier = Modifier
                        .padding(horizontal = type.horizontalPadding)
                        .size(type.iconSize),
                )
            }

            Text(
                text = label,
                size = type.textSize,
                color = actualVariant.tint,
                weight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .padding(vertical = type.verticalPadding)
                    .modifyIf(iconsOnExtremes) {
                        weight(1f)
                    },
            )

            if (config.trailingIcon != null) {
                Icon(
                    painter = config.trailingIcon.painter,
                    contentDescription = config.trailingIcon.description,
                    tint = actualVariant.tint.composeColor,
                    modifier = Modifier
                        .padding(horizontal = type.horizontalPadding)
                        .size(type.iconSize),
                )
            }
        },
    )
}

@Composable
internal fun CoreButton(
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    val borderStroke = when (variant.borderColor) {
        is BeautifulBrush -> BorderStroke(
            width = 2.dp,
            brush = variant.borderColor.linearGradient(),
        )

        is BeautifulColor -> BorderStroke(
            width = 2.dp,
            color = variant.borderColor.composeColor,
        )
    }

    Row(
        content = content,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(shape)
            .modifyIf(enabled) {
                clickable { onClick() }
            }
            .border(
                border = borderStroke,
                shape = shape,
            )
            .composed {
                when (variant.backgroundColor) {
                    is BeautifulBrush -> background(variant.backgroundColor.linearGradient())
                    is BeautifulColor -> background(variant.backgroundColor.composeColor)
                }
            }
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
    )
}
