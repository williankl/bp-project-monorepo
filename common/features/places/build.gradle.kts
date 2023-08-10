plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.features.places"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)

    commonMainImplementation(libs.voyager.navigator)
}