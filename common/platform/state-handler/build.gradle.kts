plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.platform.stateHandler"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.design.components)

    commonMainApi(libs.kodein.core)
    commonMainApi(libs.voyager.kodein)
    commonMainApi(libs.voyager.navigator)
    commonMainApi(libs.voyager.bottomSheetNavigator)

    commonMainApi(libs.kotlinx.coroutines.core)
    commonMainApi(compose.runtime)
    commonMainApi(compose.foundation)
}