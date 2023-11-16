package williankl.bpProject.common.features.places.creating.create

import androidx.compose.ui.graphics.ImageBitmap
import com.benasher44.uuid.uuid4
import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromString
import dev.icerock.moko.media.Bitmap
import dev.icerock.moko.media.compose.toImageBitmap
import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.Place.ImageData
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.network.request.SavingPlaceRequest
import williankl.bpProject.common.data.firebaseIntegration.FirebaseIntegration
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.data.placeService.services.PlacesService
import williankl.bpProject.common.features.places.creating.create.PlaceCreationRunnerModel.PlaceCreationPresentation
import williankl.bpProject.common.features.places.creating.create.handler.CreationHandler
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class PlaceCreationRunnerModel(
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

    fun retrievePresentation(images: List<Bitmap>) = setContent {
        PlaceCreationPresentation(
            images = images.map { image -> image.toImageBitmap() }
        )
    }

    fun publishPlace(
        images: List<Bitmap>,
        onPublished: () -> Unit,
    ) = runAsync {
        val selectedAddress = creationHandler.selectedAddress
            ?: error("Could not retrieve address")

        val imageUploadResults = firebaseIntegration.uploadPlacesImages(images)

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
                        position = index,
                    )
                },
                seasons = creationHandler.selectedSeasons,
                state = Place.PlaceState.Published,
                tags = emptyList(),
            )
        )

        onPublished()
    }
}
