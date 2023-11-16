package williankl.bpProject.common.features.places

import com.benasher44.uuid.Uuid
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import org.kodein.di.bindProvider
import org.kodein.di.instance
import williankl.bpProject.common.core.models.PlaceQualifier
import williankl.bpProject.common.data.placeService.models.MapServiceType
import williankl.bpProject.common.features.places.creating.create.PlaceCreationRunnerModel
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel
import williankl.bpProject.common.features.places.lisitng.PlaceListingRunnerModel
import williankl.bpProject.common.features.places.creating.photoSelection.PhotoSelectionRunnerModel
import williankl.bpProject.common.features.places.creating.searchScreen.PlaceSearchRunnerModel

public val placesDi: DI.Module = DI.Module("williankl.bpProject.common.features.places") {
    bindProvider {
        PhotoSelectionRunnerModel(
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        PlaceCreationRunnerModel(
            placesService = instance(),
            firebaseIntegration = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindProvider {
        PlaceSearchRunnerModel(
            mapsService = instance(MapServiceType.Client),
            mapController = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindMultiton<Uuid, PlaceDetailsRunnerModel> { placeId ->
        PlaceDetailsRunnerModel(
            placeId = placeId,
            session = instance(),
            ratingService = instance(),
            placesService = instance(),
            uriNavigator = instance(),
            userLocationService = instance(),
            dispatcher = Dispatchers.Default,
        )
    }

    bindMultiton<PlaceQualifier, PlaceListingRunnerModel> { qualifier ->
        PlaceListingRunnerModel(
            placeQualifier = qualifier,
            placesService = instance(),
            mapsService = instance(MapServiceType.Client),
            userLocationService = instance(),
            dispatcher = Dispatchers.Default,
        )
    }
}
