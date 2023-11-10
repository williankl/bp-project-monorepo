package williankl.bpProject.common.features.places.creating.create.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.input.InputVariant
import williankl.bpProject.common.platform.design.core.modifyIf
import williankl.bpProject.common.platform.design.core.text.Text

@Composable
internal fun InputOption(
    headerPainter: Painter,
    text: String,
    hint: String,
    onTextChange: (String) -> Unit,
    inputHeight: Dp? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = headerPainter,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )

        Input(
            text = text,
            hint = hint,
            onTextChange = onTextChange,
            variant = InputVariant.Primary,
            generalAlignment = Alignment.Top,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            modifier = Modifier
                .modifyIf(inputHeight != null) {
                    heightIn(min = inputHeight ?: 0.dp)
                }
                .weight(1f),
        )
    }
}
