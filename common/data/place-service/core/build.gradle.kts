import williankl.bpProject.buildSrc.helpers.addJvmTarget

plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.data.placeService.core"
}

addJvmTarget()

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.networking.core)

    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.serialization.json)
}