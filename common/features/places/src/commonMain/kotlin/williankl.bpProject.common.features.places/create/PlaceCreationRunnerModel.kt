package williankl.bpProject.common.features.places.create

import androidx.compose.ui.graphics.ImageBitmap
import com.benasher44.uuid.uuid4
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.ImageData
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever
import williankl.bpProject.common.data.imageRetrievalService.toImageBitmap
import williankl.bpProject.common.data.placeService.PlacesService
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.create.PlaceCreationRunnerModel.PlaceCreationPresentation
import williankl.bpProject.common.features.places.create.handler.CreationHandler
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceCreationRunnerModel(
    private val imageRetriever: ImageRetriever,
    private val placesService: PlacesService,
    private val firebaseIntegration: FirebaseIntegration,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<PlaceCreationPresentation>(
    dispatcher = dispatcher,
    initialData = PlaceCreationPresentation(),
) {

    internal val creationHandler by lazy {
        CreationHandler()
    }

    internal data class PlaceCreationPresentation(
        val suggestedPlaces: List<MapPlaceResult> = emptyList(),
        val images: List<ImageBitmap> = emptyList(),
    )

    fun retrievePresentation(uriList: List<String>) = setContent {
        val parsedUriList = uriList.map(Uri::fromString)

        PlaceCreationPresentation(
            images = parsedUriList
                .map(imageRetriever::retrieveImageFromUri)
                .map { bitmap -> bitmap.toImageBitmap() }
        )
    }

    fun publishImage(
        imageUriList: List<String>,
    ) = runAsync {
        val selectedAddress = creationHandler.selectedAddress
            ?: error("Could not retrieve address")

        val imageBitmaps = imageUriList.map { uriString ->
            val uri = Uri.fromString(uriString)
            imageRetriever.retrieveImageFromUri(uri)
        }

        val imageUploadResults = firebaseIntegration.uploadPlacesImages(imageBitmaps)

        placesService.saveNewPlace(
            place = SavingPlaceRequest(
                name = selectedAddress.displayName,
                description = creationHandler.notes.ifBlank { null },
                address = with(selectedAddress) {
                    PlaceAddress(
                        id = uuid4(),
                        street = address.street,
                        city = address.city,
                        country = address.country,
                        coordinates = MapCoordinate(
                            latitude = coordinate.latitude,
                            longitude = coordinate.longitude
                        ),
                    )
                },
                images = imageUploadResults.mapIndexed { index, result ->
                    ImageData(
                        id = uuid4(),
                        url = result.url,
                        originalUrl = result.originalImageUrl,
                        position = index,
                    )
                },
                seasons = creationHandler.selectedSeasons,
                state = Place.PlaceState.Published,
                tags = emptyList(),
            )
        )
    }
}
