package williankl.bpProject.common.features.places.creating.photoSelection

import androidx.compose.ui.graphics.Color
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import dev.icerock.moko.media.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.averageColor
import williankl.bpProject.common.features.places.creating.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.nonComposableColor
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PhotoSelectionRunnerModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PhotoSelectionPresentation>(
    dispatcher = dispatcher,
    initialData = PhotoSelectionPresentation()
) {

    internal companion object {
        val defaultImageColor = BeautifulColor.Secondary.nonComposableColor()
    }

    internal data class PhotoSelectionPresentation(
        val imageDataList: List<SelectedImageData> = emptyList()
    ) {
        internal data class SelectedImageData(
            val id: Uuid,
            val image: Bitmap,
            val averageColor: Color,
        )
    }

    fun retrievePresentation(images: List<Bitmap>) = setContent {
        val parsedUriList = images.map { image ->
            PhotoSelectionPresentation.SelectedImageData(
                id = uuid4(),
                image = image,
                averageColor = image.averageColor(),
            )
        }

        PhotoSelectionPresentation(
            imageDataList = parsedUriList,
        )
    }

    fun handleImageRemoval(uuid: Uuid) = runAsync {
        updateData { currentData ->
            currentData.copy(
                imageDataList = currentData.imageDataList
                    .filter { data -> data.id != uuid }
            )
        }
    }
}
