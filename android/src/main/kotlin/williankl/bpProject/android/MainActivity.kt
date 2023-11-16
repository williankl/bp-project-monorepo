package williankl.bpProject.android

import android.Manifest
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import dev.icerock.moko.media.picker.MediaPickerController
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import williankl.bpProject.common.application.AppContent

internal class MainActivity : FragmentActivity(), DIAware {

    override val di: DI by closestDI()
    private val mediaPickerController by instance<MediaPickerController>()

    private val locationPermissionRetriever =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestResult,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mediaPickerController.bind(lifecycle, supportFragmentManager)

        locationPermissionRetriever.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            AppContent()
        }
    }

    private fun onPermissionRequestResult(
        permissionMap: Map<String, Boolean>
    ) {
        // todo - handle result if needed
    }


}
