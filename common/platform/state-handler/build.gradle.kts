plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.platform.stateHandler"
}

dependencies {
    commonMainImplementation(projects.common.platform.design.core)
    commonMainApi(libs.voyager.navigator)
    commonMainApi(libs.voyager.kodein)
    commonMainApi(libs.kotlinx.coroutines.core)
    commonMainApi(compose.runtime)
    commonMainApi(compose.foundation)
}