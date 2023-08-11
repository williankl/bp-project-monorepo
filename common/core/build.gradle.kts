import williankl.bpProject.buildSrc.helpers.applyJvmTarget

plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.core"
}

applyJvmTarget()

dependencies {
    commonMainImplementation(libs.uuid)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
}