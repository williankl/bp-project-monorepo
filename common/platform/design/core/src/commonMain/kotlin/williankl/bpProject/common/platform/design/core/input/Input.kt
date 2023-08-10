package williankl.bpProject.common.platform.design.core.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulBrush
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.hover
import williankl.bpProject.common.platform.design.core.modifyIf
import williankl.bpProject.common.platform.design.core.retrieveLikeAPProFontFamily
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

@Composable
public fun Input(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    size: TextSize = TextSize.Regular,
    fontStyle: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    sideContentsOnExtremes: Boolean = false,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    CoreInput(
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        hint = hint,
        size = size,
        fontStyle = fontStyle,
        weight = weight,
        enabled = enabled,
        readOnly = readOnly,
        color = color,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        sideContentsOnExtremes = sideContentsOnExtremes,
        startContent = startContent,
        endContent = endContent,
    )
}

@Composable
@OptIn(ExperimentalTextApi::class)
private fun CoreInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    size: TextSize = TextSize.Regular,
    fontStyle: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    sideContentsOnExtremes: Boolean = false,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    var borderColor: BeautifulColor by remember {
        mutableStateOf(BeautifulColor.Surface)
    }

    val style = when (color) {
        is BeautifulColor -> TextStyle(
            color = color.composeColor(enabled.not()),
        )

        is BeautifulBrush -> TextStyle(
            brush = Brush.linearGradient(
                colors = color.colors.map { proColor -> proColor.composeColor }
            ),
        )
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.onFocusChanged { state ->
            borderColor =
                if (state.isFocused) BeautifulColor.Alert
                else BeautifulColor.Surface
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = style.copy(
            fontStyle = fontStyle,
            fontSize = size.textUnit,
            fontWeight = weight,
            fontFamily = retrieveLikeAPProFontFamily()
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = { /* Nothing */ },
        interactionSource = interactionSource,
        cursorBrush = SolidColor(BeautifulColor.NeutralHigh.composeColor),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = borderColor.composeColor,
                        shape = BeautifulShape.Rounded.Regular.composeShape,
                    )
                    .background(
                        color = BeautifulColor.SurfaceHigh.composeColor,
                        shape = BeautifulShape.Rounded.Regular.composeShape,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    )
            ) {
                AnimatedVisibility(startContent != null) {
                    startContent?.invoke()
                }

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .modifyIf(sideContentsOnExtremes) {
                            weight(1f)
                        }
                ) {
                    if (hint.isNullOrBlank().not() && text.isBlank()) {
                        Text(
                            text = hint.orEmpty(),
                            size = size,
                            color = BeautifulColor.NeutralHigh.hover,
                        )
                    }

                    innerTextField()
                }

                AnimatedVisibility(endContent != null) {
                    endContent?.invoke()
                }
            }
        },
    )
}
