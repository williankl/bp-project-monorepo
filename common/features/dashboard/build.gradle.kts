plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.features.dashboard"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)
    commonMainImplementation(libs.moko.resourcesCompose)
}