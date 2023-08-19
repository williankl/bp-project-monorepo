plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.data.imageRetrievalService"
}

dependencies {
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.moko.resourcesCompose)
}