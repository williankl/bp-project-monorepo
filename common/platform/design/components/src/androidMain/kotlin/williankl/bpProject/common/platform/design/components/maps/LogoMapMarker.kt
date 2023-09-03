package williankl.bpProject.common.platform.design.components.maps

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources

@Composable
@GoogleMapComposable
internal fun LogoMapMarker(
    position: MapCoordinate,
    onMarkerClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MarkerComposable(
        onClick = {
            onMarkerClicked()
            false
        },
        state = MarkerState(
            position = LatLng(position.latitude, position.longitude)
        ),
    ) {
        Image(
            painter = painterResource(SharedDesignCoreResources.images.bp_logo_dark),
            contentDescription = null,
            modifier = modifier
        )
    }
}
