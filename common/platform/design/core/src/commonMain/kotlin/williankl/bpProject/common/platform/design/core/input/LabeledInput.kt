package williankl.bpProject.common.platform.design.core.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

@Composable
public fun LabeledInput(
    label: String,
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = BeautifulColor.NeutralHigh,
            weight = FontWeight.Bold,
            style = FontStyle.Italic,
        )

        Input(
            text = text,
            onTextChange = onTextChange,
            modifier = Modifier,
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
}
