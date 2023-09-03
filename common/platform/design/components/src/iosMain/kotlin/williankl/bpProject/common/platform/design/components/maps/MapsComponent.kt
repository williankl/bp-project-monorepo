package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import williankl.bpProject.common.core.models.MapCoordinate

@Composable
public actual fun MapsComponent(
    currentMarkedPlace: MapCoordinate?,
    onClearPlaceRequested: () -> Unit,
    onPlaceSelected: (MapCoordinate) -> Unit,
    modifier: Modifier,
) {
    TODO("Yet to be implemented")
}
