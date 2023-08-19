package williankl.bpProject.common.data.imageRetrievalService

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet.ImageRequestOptions

public class ImageRetrievalController(
    private val onRetrieveRequested: (RetrievalMode) -> Unit,
) {

    private val mutableImageFlow = MutableStateFlow<List<ImageBitmap>>(emptyList())
    public val imageFlow: StateFlow<List<ImageBitmap>> = mutableImageFlow

    public fun requestImage(mode: RetrievalMode) {
        onRetrieveRequested(mode)
    }

    public fun publishImages(
        images: List<ImageBitmap>
    ) {
        mutableImageFlow.update { images }
    }


    public fun showBottomSheet(
        bottomSheetNav: BottomSheetNavigator
    ) {
        bottomSheetNav.show(
            ImageRequestBottomSheet(
                onOptionSelected = { option ->
                    bottomSheetNav.hide()
                    requestImage(
                        when (option) {
                            ImageRequestOptions.ImportFromGallery -> RetrievalMode.Gallery
                            ImageRequestOptions.Camera -> RetrievalMode.Camera
                        }
                    )
                }
            )
        )
    }
}
