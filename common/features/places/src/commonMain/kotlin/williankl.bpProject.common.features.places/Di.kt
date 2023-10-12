package williankl.bpProject.common.features.places

import com.benasher44.uuid.Uuid
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import org.kodein.di.bindProvider
import org.kodein.di.instance
import williankl.bpProject.common.data.placeService.models.MapServiceType
import williankl.bpProject.common.features.places.create.PlaceCreationRunnerModel
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel

public val placesDi: DI.Module = DI.Module("williankl.bpProject.common.features.places") {
    bindProvider {
        PhotoSelectionRunnerModel(
            imageRetriever = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        PlaceCreationRunnerModel(
            imageRetriever = instance(),
            placesService = instance(),
            firebaseIntegration = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        PlaceSearchRunnerModel(
            mapsService = instance(MapServiceType.Client),
            dispatcher = Dispatchers.Default,
        )
    }

    bindMultiton<Uuid, PlaceDetailsRunnerModel> { placeId ->
        PlaceDetailsRunnerModel(
            placeId = placeId,
            session = instance(),
            ratingService = instance(),
            dispatcher = Dispatchers.Default,
        )
    }
}
