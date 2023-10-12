package williankl.bpProject.common.application

import org.kodein.di.DI
import williankl.bpProject.common.core.commonCoreDi
import williankl.bpProject.common.data.auth.authServiceDi
import williankl.bpProject.common.data.cypher.cypherDi
import williankl.bpProject.common.data.firebaseIntegration.firebaseIntegrationDi
import williankl.bpProject.common.data.imageRetrievalService.imageRetrievalServiceDi
import williankl.bpProject.common.data.networking.networkingDi
import williankl.bpProject.common.data.placeService.placesServiceDi
import williankl.bpProject.common.data.sessionHandler.platformSessionHandlerDi
import williankl.bpProject.common.data.sessionHandler.sessionHandlerDi
import williankl.bpProject.common.features.authentication.authenticationFeatureDi
import williankl.bpProject.common.features.dashboard.dashboardDi
import williankl.bpProject.common.features.places.placesDi

public val applicationDi: DI.Module = DI.Module("williankl.bpProject.common.application") {
    import(cypherDi)
    import(commonCoreDi)
    import(imageRetrievalServiceDi)
    import(firebaseIntegrationDi)
    import(networkingDi)
    import(platformSessionHandlerDi)
    import(sessionHandlerDi)
    import(authServiceDi)
    import(placesServiceDi)
    import(authenticationFeatureDi)
    import(dashboardDi)
    import(placesDi)
}
