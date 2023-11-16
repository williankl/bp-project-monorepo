package williankl.bpProject.common.data.imageRetrievalService.controller

import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import dev.icerock.moko.media.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet

public class ImageRetrievalController {

    private val mutablePublishedImages = MutableStateFlow(emptyList<Bitmap>())
    public val publishedImages: StateFlow<List<Bitmap>> = mutablePublishedImages

    public fun clearPublishedImages() {
        mutablePublishedImages.update { emptyList() }
    }

    public fun publishImage(bitmap: Bitmap) {
        mutablePublishedImages.update { previous -> previous + bitmap }
    }

    public fun showBottomSheet(
        bottomSheetNav: BottomSheetNavigator,
        onImagePublished: () -> Unit = { /* Nothing by default */ }
    ) {
        bottomSheetNav.show(
            ImageRequestBottomSheet {
                bottomSheetNav.hide()
                onImagePublished()
            }
        )
    }
}
