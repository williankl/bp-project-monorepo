package williankl.bpProject.common.features.places.searchScreen

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel.PhotoSelectionPresentation
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceSearchRunnerModel(
    private val imageRetriever: ImageRetriever,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceSearchPresentation>(
    dispatcher = dispatcher,
    initialData = PlaceSearchPresentation()
) {

    internal data class PlaceSearchPresentation(
        val queryResults: List<AddressPresentation> = emptyList()
    ) {
        internal data class AddressPresentation(
            val city: String,
            val street: String,
            val country: String,
        )
    }
}
