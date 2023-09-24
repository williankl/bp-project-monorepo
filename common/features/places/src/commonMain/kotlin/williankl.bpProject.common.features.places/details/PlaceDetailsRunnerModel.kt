package williankl.bpProject.common.features.places.details

import androidx.compose.ui.graphics.Color
import com.chrynan.uri.core.Uri
import korlibs.image.bitmap.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.nonComposableColor
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceDetailsRunnerModel(
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceDetailsPresentation>(
    initialData = PlaceDetailsPresentation(),
    dispatcher = dispatcher,
) {
    internal companion object {
        val defaultImageColor = BeautifulColor.Secondary.nonComposableColor()
    }
    internal data class PlaceDetailsPresentation(
        val averageColorList: List<Color> = emptyList(),
    )
}