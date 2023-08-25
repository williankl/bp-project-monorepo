import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "williankl.bpProject.common.data.imageRetrievalService"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.data.imageRetrievalService")

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)

    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.moko.resourcesCompose)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)

    androidMainImplementation(libs.android.exifInterface)
    androidMainImplementation(libs.android.androidx.core)
}