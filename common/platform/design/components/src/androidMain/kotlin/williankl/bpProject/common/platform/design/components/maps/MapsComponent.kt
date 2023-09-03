package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import williankl.bpProject.common.core.models.MapCoordinate

@Composable
public actual fun MapsComponent(
    currentMarkedPlace: MapCoordinate?,
    onClearPlaceRequested: () -> Unit,
    onPlaceSelected: (MapCoordinate) -> Unit,
    modifier: Modifier,
) {
    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true
        )
    }
    val mapUiSettings = remember {
        MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = true,
            rotationGesturesEnabled = true,
            scrollGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,
            tiltGesturesEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = true,
        )
    }

    GoogleMap(
        properties = mapProperties,
        uiSettings = mapUiSettings,
        modifier = modifier,
        onMapClick = { coordinates ->
            onPlaceSelected(
                MapCoordinate(
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude,
                )
            )
        },
        content = {
            if (currentMarkedPlace != null) {
                LogoMapMarker(
                    position = currentMarkedPlace,
                    onMarkerClicked = onClearPlaceRequested,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}
