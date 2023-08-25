package williankl.bpProject.android

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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.bpProject.common.data.imageRetrievalService.ImageCaptureHelper
import williankl.bpProject.common.data.imageRetrievalService.controller.ImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.data.imageRetrievalService.controller.RetrievalMode
import williankl.bpProject.common.features.dashboard.DashboardScreen
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.colors.composeHoverColor
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import android.net.Uri as AndroidUri

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
                        val blurDp = if (bottomSheetNav.isVisible) 12.dp else 0.dp
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
}
