import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.core"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.core")

dependencies {
    commonMainImplementation(projects.common.platform.design.core)

    commonMainApi(libs.kmm.uuid)
    commonMainApi(libs.kmm.uri)
    commonMainApi(libs.kmm.bitmap)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(compose.runtime)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)
}
