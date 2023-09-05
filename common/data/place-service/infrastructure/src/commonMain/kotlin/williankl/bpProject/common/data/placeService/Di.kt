package williankl.bpProject.common.data.placeService

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.networking.ClientType
import williankl.bpProject.common.data.placeService.internal.MapsServiceInfrastructure
import williankl.bpProject.common.data.placeService.internal.PlacesServiceInfrastructure

public val placesServiceDi: DI.Module = DI.Module("williankl.bpProject.common.data.placeService") {

    bindSingleton<PlacesService> {
        PlacesServiceInfrastructure(
            client = instance(ClientType.BeautifulPlaces),
            mapsServiceInfrastructure = instance(),
        )
    }

    bindSingleton<MapsServiceInfrastructure> {
        MapsServiceInfrastructure(
            placesClient = instance(ClientType.GooglePlaces),
        )
    }
}
