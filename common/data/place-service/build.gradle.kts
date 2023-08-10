plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.data.placeService"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(libs.uuid)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.kotlinx.serialization.core)
}