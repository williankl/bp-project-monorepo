package williankl.bpProject.common.features.places.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.platform.design.core.clickableIcon

public fun LazyListScope.lazyWeightedPlaceDisplays(
    places: List<PlaceDisplayPresentation>,
    onPlaceSelected: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    val splitPlaces = places.chunked(4)
    items(splitPlaces) { placesChunk ->
        WeightedPlacesDisplay(
            places = placesChunk,
            onPlaceSelected = onPlaceSelected,
            modifier = modifier,
        )
    }
}

@Composable
public fun WeightedPlacesDisplay(
    places: List<PlaceDisplayPresentation>,
    onPlaceSelected: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    @Composable
    fun WeightedColumn(
        firstBigger: Boolean,
        places: List<PlaceDisplayPresentation>,
        modifier: Modifier = Modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier,
        ) {
            places.forEachIndexed { index, placePresentation ->
                val weight = when {
                    index == 0 && firstBigger -> 1f
                    index != 0 && firstBigger.not() -> 1f
                    else -> 0.8f
                }

                PlaceDisplay(
                    placeDisplayPresentation = placePresentation,
                    imageModifier = Modifier.weight(1f),
                    modifier = Modifier
                        .clickableIcon(0.dp) { onPlaceSelected(placePresentation.place) }
                        .weight(weight),
                )
            }
        }
    }

    val validColumns = remember(places) {
        places.chunked(2)
            .take(2)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
    ) {
        validColumns.forEachIndexed { index, columnPlaces ->
            WeightedColumn(
                firstBigger = index % 2 == 0,
                places = columnPlaces,
                modifier = Modifier.weight(1f),
            )
        }
    }
}