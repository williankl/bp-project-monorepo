package williankl.bpProject.common.data.placeService.deviceLocation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.UserLocationService

internal actual class UserLocationRetriever(
    private val context: Context,
) : UserLocationService {

    private val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun currentUserCoordinates(): MapCoordinate? {
        return withPermissionOrNull {
            val result = locationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).result
            MapCoordinate(
                latitude = result.latitude,
                longitude = result.longitude,
            )
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun lastUserCoordinates(): MapCoordinate? {
        return withPermissionOrNull {
            val result = locationClient.lastLocation.result
            MapCoordinate(
                latitude = result.latitude,
                longitude = result.longitude,
            )
        }
    }

    private suspend fun <T> withPermissionOrNull(
        action: suspend () -> T,
    ): T? {
        return with(context) {
            val hasFineLocationPermission =
                ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

            val hasCoarseLocationPermission =
                ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

            if (hasFineLocationPermission && hasCoarseLocationPermission) action()
            else null
        }
    }
}
