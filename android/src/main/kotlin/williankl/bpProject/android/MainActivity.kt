package williankl.bpProject.android

import android.Manifest
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import williankl.bpProject.common.application.AppContent
import williankl.bpProject.common.data.imageRetrievalService.ImageCaptureHelper
import williankl.bpProject.common.data.imageRetrievalService.controller.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.controller.RetrievalMode
import williankl.bpProject.common.platform.design.components.toolbar.ToolbarHandler
import android.net.Uri as AndroidUri

internal class MainActivity : ComponentActivity() {

    private val toolbarHandler by lazy {
        ToolbarHandler()
    }

    private val imageRetriever =
        registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4),
            ::onImagePickResult,
        )

    private val cameraImageRetriever =
        registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            ::onImageTaken,
        )

    private val locationPermissionRetriever =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestResult,
        )

    private val imageRetrievalController by lazy {
        ImageRetrievalController(
            onRetrieveRequested = { mode ->
                when (mode) {
                    RetrievalMode.Gallery -> imageRetriever.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )

                    RetrievalMode.Camera -> cameraImageRetriever.launch(
                        ImageCaptureHelper.cacheUri(this)
                    )
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        locationPermissionRetriever.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            AppContent(imageRetrievalController, toolbarHandler)
        }
    }

    private fun onImagePickResult(uris: List<AndroidUri>) {
        val images = uris.map { uri -> uri.toString() }

        imageRetrievalController.publishImages(images)
    }

    private fun onImageTaken(takenImage: Boolean) {
        val cachedUri = ImageCaptureHelper.cacheUri(this)
        if (takenImage && cachedUri != null) {
            imageRetrievalController.publishImages(listOf(cachedUri.toString()))
        }
    }

    private fun onPermissionRequestResult(
        permissionMap: Map<String, Boolean>
    ) {
        // todo - handle result if needed
    }
}
