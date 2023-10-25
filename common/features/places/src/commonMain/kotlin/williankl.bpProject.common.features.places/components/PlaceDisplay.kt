package williankl.bpProject.common.features.places.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

public data class PlaceDisplayPresentation(
    val distanceLabel: Long?,
    val place: Place,
)

@Composable
public fun PlaceDisplay(
    placeDisplayPresentation: PlaceDisplayPresentation,
    imageModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
) {
    val strings = LocalPlacesStrings.current
    Column(
        modifier, Arrangement.spacedBy(4.dp),
    ) {
        AsyncImage(
            url = placeDisplayPresentation.place.images.firstOrNull()?.url.orEmpty(),
            contentScale = ContentScale.Crop,
            modifier = imageModifier.clip(BeautifulShape.Rounded.Regular.composeShape),
        )

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = placeDisplayPresentation.place.displayName,
                size = TextSize.Large,
                weight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f),
            )

            placeDisplayPresentation.distanceLabel?.let { distance ->
                Text(
                    text = "(${strings.distanceLabel(distance)})",
                    size = TextSize.Small,
                )
            }
        }
    }
}
