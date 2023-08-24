package williankl.bpProject.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.bpProject.common.features.authentication.AuthenticationScreen
import williankl.bpProject.common.core.ImageCaptureHelper
import williankl.bpProject.common.core.ImageUriHandler
import williankl.bpProject.common.data.imageRetrievalService.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.LocalImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.RetrievalMode
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import williankl.bpProject.common.platform.stateHandler.UIState
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

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

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeautifulThemeContent {
                CompositionLocalProvider(
                    LocalImageRetrievalController provides imageRetrievalController
                ) {
                    BottomSheetNavigator(
                        scrimColor = BeautifulColor.Black.composeHoverColor,
                        sheetBackgroundColor = BeautifulColor.Background.composeColor,
                        sheetShape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                        ),
                    ) { bottomSheetNav ->
                        val blurDp = if (bottomSheetNav.isVisible) 6.dp else 0.dp
                        val animatedBlurDp by animateDpAsState(
                            label = "content-blur-dp",
                            targetValue = blurDp
                        )

                        Navigator(
                            screen = DashboardScreen,
                            onBackPressed = { true }
                        ) { nav ->
                            Box(
                                modifier = Modifier.blur(animatedBlurDp)
                            ) {
                                SlideTransition(nav)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onImagePickResult(uris: List<Uri>) {
        val images = uris.map { uri ->
            ImageUriHandler.retrieveImageFromUri(uri, contentResolver).asImageBitmap()
        }
        imageRetrievalController.publishImages(images)
    }

    private fun onImageTaken(takenImage: Boolean) {
        val cachedUri = ImageCaptureHelper.cacheUri(this)
        if (takenImage && cachedUri != null) {
            val result = ImageUriHandler.retrieveImageFromUri(cachedUri, contentResolver).asImageBitmap()
            imageRetrievalController.publishImages(listOf(result))
        }
    }
}
