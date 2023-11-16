package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.maps.google.GoogleMapController
import williankl.bpProject.common.core.models.MapCoordinate

@Composable
public expect fun MapsComponent(
    onMapReady: () -> Unit,
    modifier: Modifier,
)

public val LocalMapController: ProvidableCompositionLocal<GoogleMapController?> =
    staticCompositionLocalOf { null }

public fun LatLng.toMapCoordinate(): MapCoordinate {
    return MapCoordinate(
        latitude = latitude,
        longitude = longitude,
    )
}

public fun MapCoordinate.toMapCoordinate(): LatLng {
    return LatLng(
        latitude = latitude,
        longitude = longitude,
    )
}
