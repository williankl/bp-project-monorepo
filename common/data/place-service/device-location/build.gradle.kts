plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.placeService.deviceLocation"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.placeService.core)
    commonMainImplementation(libs.kodein.core)

    androidMainImplementation(libs.googlePlayServices.location)
}