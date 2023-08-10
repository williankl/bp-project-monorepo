plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.platform.stateHandler"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(libs.voyager.navigator)
    commonMainApi(libs.kotlinx.coroutines.core)
    commonMainApi(compose.runtime)
}