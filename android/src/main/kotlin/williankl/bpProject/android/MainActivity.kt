package williankl.bpProject.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import korlibs.image.format.AndroidNativeImage
import williankl.bpProject.android.internal.ImageCaptureHelper
import williankl.bpProject.android.internal.ImageUriHandler
import williankl.bpProject.common.data.imageRetrievalService.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.LocalImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.RetrievalMode
import williankl.bpProject.common.features.authentication.LoginScreen
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent

internal class MainActivity : ComponentActivity() {

    private val imageRetriever =
        registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4),
            ::onImagePickResult
        )

    private val cameraImageRetriever =
        registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            ::onImageTaken
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

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeautifulThemeContent {
                CompositionLocalProvider(
                    LocalImageRetrievalController provides imageRetrievalController
                ){
                    Navigator(LoginScreen) {
                        SlideTransition(it)
                    }
                }
            }
        }
    }

    private fun onImagePickResult(uris: List<Uri>) {
        val images = uris.map { uri ->
            val result = ImageUriHandler.retrieveImageFromUri(uri, contentResolver)
            AndroidNativeImage(result)
        }
        imageRetrievalController.publishImages(images)
    }

    private fun onImageTaken(takenImage: Boolean) {
        val cachedUri = ImageCaptureHelper.cacheUri(this)
        if (takenImage && cachedUri != null) {
            val result = ImageUriHandler.retrieveImageFromUri(cachedUri, contentResolver)
            val kmmImage = AndroidNativeImage(result)
            imageRetrievalController.publishImages(listOf(kmmImage))
        }
    }
}
