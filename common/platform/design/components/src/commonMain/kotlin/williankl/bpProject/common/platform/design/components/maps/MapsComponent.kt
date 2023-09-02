package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public expect fun MapsComponent(
    currentMarkedPlace: MapCoordinate?,
    onClearPlaceRequested: () -> Unit,
    onPlaceSelected: (MapCoordinate) -> Unit,
    modifier: Modifier = Modifier,
)