plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.core"
}

dependencies {
    commonMainApi(libs.kmm.uuid)
    commonMainApi(libs.kmm.uri)
    commonMainApi(libs.kmm.bitmap)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
}