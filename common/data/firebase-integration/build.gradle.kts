plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.firebaseIntegration"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.data.sessionHandler)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.moko.media.core)

    androidMainImplementation(platform(libs.firebase.bom))
    androidMainImplementation(libs.firebase.storage)
}