package williankl.bpProject.common.data.imageRetrievalService

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet.ImageRequestOptions

public class ImageRetrievalController(
    private val onRetrieveRequested: (RetrievalMode) -> Unit,
) {

    private val publishListeners by lazy {
        mutableListOf<(List<ImageBitmap>) -> Unit>()
    }

    public fun requestImage(mode: RetrievalMode) {
        onRetrieveRequested(mode)
    }

    public fun publishImages(
        images: List<ImageBitmap>
    ) {
        publishListeners.forEach { listener ->
            listener(images)
        }

        cancelOnGoingListeners()
    }

    public fun cancelOnGoingListeners() {
        publishListeners.clear()
    }

    public fun showBottomSheet(
        bottomSheetNav: BottomSheetNavigator,
        onImagePublished: (List<ImageBitmap>) -> Unit
    ) {
        bottomSheetNav.show(
            ImageRequestBottomSheet(
                onOptionSelected = { option ->
                    publishListeners.add(onImagePublished)
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
