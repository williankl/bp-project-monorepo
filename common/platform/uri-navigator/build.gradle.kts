plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.platform.uriNavigator"
}

dependencies {
    commonMainImplementation(libs.kmm.uri)
    commonMainImplementation(libs.kodein.core)
}