package williankl.bpProject.common.features.places.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

@Composable
public fun SimplePlaceDisplay(
    place: Place,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        AsyncImage(
            url = place.images.firstOrNull()?.url.orEmpty(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(BeautifulShape.Rounded.Regular.composeShape)
                .size(100.dp),
        )

        Text(
            text = place.displayName,
            size = TextSize.XSmall,
            maxLines = 2,
            modifier = Modifier.widthIn(max = 100.dp),
        )
    }
}
