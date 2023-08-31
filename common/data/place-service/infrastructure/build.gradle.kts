plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.data.placeService.infrastructure"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.data.placeService.core)

    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.kotlinx.serialization.core)
}