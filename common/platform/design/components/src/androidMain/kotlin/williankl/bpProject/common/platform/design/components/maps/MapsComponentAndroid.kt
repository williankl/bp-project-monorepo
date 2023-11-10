package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect

@Composable
public actual fun MapsComponent(
    onMapReady: () -> Unit,
    modifier: Modifier,
) {
    val mapController = LocalMapController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    GoogleMap(
        modifier = modifier,
        onMapLoaded = { onMapReady() },
        content = {
            MapEffect(mapController) { map ->
                mapController?.bind(
                    lifecycle = lifecycleOwner.lifecycle,
                    context = context,
                    googleMap = map,
                )
            }
        }
    )
}
