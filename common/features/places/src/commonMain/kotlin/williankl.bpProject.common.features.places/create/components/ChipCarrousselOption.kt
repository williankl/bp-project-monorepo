package williankl.bpProject.common.features.places.create.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

internal data class ChipOption(
    val label: String,
    val isSelected: Boolean,
    val onClicked: () -> Unit,
)

@Composable
internal fun ChipCarrousselOption(
    label: String,
    headerPainter: Painter,
    options: List<ChipOption>,
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

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = label,
                color = BeautifulColor.NeutralHigh,
                size = TextSize.Small,
                weight = FontWeight.SemiBold,
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(options) { option ->
                    Button(
                        label = option.label,
                        onClick = option.onClicked,
                        type = ButtonType.Pill,
                        variant =
                        if (option.isSelected) ButtonVariant.Primary
                        else ButtonVariant.PrimaryGhost,
                    )
                }
            }
        }
    }
}
