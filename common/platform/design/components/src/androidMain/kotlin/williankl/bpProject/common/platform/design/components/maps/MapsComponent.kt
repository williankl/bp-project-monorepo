package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import williankl.bpProject.common.core.models.MapCoordinate

@Composable
public actual fun MapsComponent(
    currentMarkedPlace: MapCoordinate?,
    onClearPlaceRequested: () -> Unit,
    onPlaceSelected: (MapCoordinate) -> Unit,
    modifier: Modifier,
) {
    val cameraPositionState = rememberCameraPositionState()
    var isMapLoaded by remember {
        mutableStateOf(false)
    }

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

    LaunchedEffect(isMapLoaded, currentMarkedPlace) {
        if (currentMarkedPlace != null) {
            moveCameraTo(cameraPositionState, currentMarkedPlace)
        }
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings,
        modifier = modifier,
        onMapLoaded = { isMapLoaded = true },
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

private suspend fun moveCameraTo(
    cameraPositionState: CameraPositionState,
    mapCoordinate: MapCoordinate,
) {
    cameraPositionState.animate(
        CameraUpdateFactory
            .newCameraPosition(
                CameraPosition(
                    LatLng(mapCoordinate.latitude, mapCoordinate.longitude),
                    15f,
                    0f,
                    0f,
                )
            )
    )
}
