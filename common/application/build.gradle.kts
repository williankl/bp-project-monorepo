plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.application"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.uriNavigator)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.design.components)
    commonMainImplementation(projects.common.platform.stateHandler)

    commonMainImplementation(projects.common.data.cypher)
    commonMainImplementation(projects.common.data.firebaseIntegration)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.data.networking.core)
    commonMainImplementation(projects.common.data.networking.internal)
    commonMainImplementation(projects.common.data.auth)
    commonMainImplementation(projects.common.data.sessionHandler)
    commonMainImplementation(projects.common.data.placeService.core)
    commonMainImplementation(projects.common.data.placeService.deviceLocation)
    commonMainImplementation(projects.common.data.placeService.infrastructure)

    commonMainImplementation(projects.common.features.authentication)
    commonMainImplementation(projects.common.features.dashboard)
    commonMainImplementation(projects.common.features.places)

    commonMainImplementation(compose.material)
    commonMainImplementation(libs.voyager.navigator)
    commonMainImplementation(libs.voyager.transitions)

    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.kodein.compose)
    androidMainImplementation(libs.kodein.android)
}
