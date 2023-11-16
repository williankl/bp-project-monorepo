import williankl.bpProject.buildSrc.helpers.addJvmTarget

plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.data.placeService.infrastructure"
}

addJvmTarget()

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.placeService.core)
    commonMainImplementation(projects.common.data.networking.core)

    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.kotlinx.serialization.core)
}