package williankl.bpProject.common.data.imageRetrievalService.controller

import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet
import williankl.bpProject.common.data.imageRetrievalService.components.ImageRequestBottomSheet.ImageRequestOptions

public class ImageRetrievalController(
    private val onRetrieveRequested: (RetrievalMode) -> Unit,
) {

    private val publishListeners by lazy {
        mutableListOf<(List<String>) -> Unit>()
    }

    public fun requestImage(mode: RetrievalMode) {
        onRetrieveRequested(mode)
    }

    public fun publishImages(
        images: List<String>
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
        onImagePublished: (List<String>) -> Unit
    ) {
        bottomSheetNav.show(
            ImageRequestBottomSheet(
                onOptionSelected = { option ->
                    bottomSheetNav.hide()
                    publishListeners.add(onImagePublished)
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
