package williankl.bpProject.common.data.imageRetrievalService

import korlibs.image.bitmap.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

public class ImageRetrievalController(
    private val onRetrieveRequested: (RetrievalMode) -> Unit,
) {

    private val mutableImageFlow = MutableStateFlow<List<Bitmap>>(emptyList())
    public val imageFlow: StateFlow<List<Bitmap>> = mutableImageFlow

    public fun requestImage(mode: RetrievalMode) {
        onRetrieveRequested(mode)
    }

    public fun publishImages(
        images: List<Bitmap>
    ) {
        mutableImageFlow.update { images }
    }

}