package williankl.bpProject.common.data.placeService

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.ClientType
import williankl.bpProject.common.data.placeService.internal.ClientMapsServiceInfrastructure
import williankl.bpProject.common.data.placeService.internal.ServerMapsServiceInfrastructure
import williankl.bpProject.common.data.placeService.internal.infrastructure.PlaceRatingServiceInfrastructure
import williankl.bpProject.common.data.placeService.internal.infrastructure.PlacesServiceInfrastructure
import williankl.bpProject.common.data.placeService.models.MapServiceType

public val placesServiceDi: DI.Module = DI.Module("williankl.bpProject.common.data.placeService") {

    bindSingleton<PlacesService> {
        PlacesServiceInfrastructure(
            client = instance(),
        )
    }

    bindSingleton<PlaceRatingService> {
        PlaceRatingServiceInfrastructure(
            client = instance(),
        )
    }

    bindSingleton<MapsService>(MapServiceType.Server) {
        ServerMapsServiceInfrastructure(
            placesClient = instance(ClientType.GooglePlaces),
            mapsClient = instance(ClientType.GoogleMaps),
        )
    }

    bindSingleton<MapsService>(MapServiceType.Client) {
        ClientMapsServiceInfrastructure(
            client = instance()
        )
    }
}
