import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "williankl.bpProject.common.features.places"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.features.places")

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.placeService.core)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.design.components)
    commonMainImplementation(projects.common.platform.stateHandler)

    commonMainImplementation(libs.voyager.navigator)
    commonMainImplementation(libs.moko.resourcesCompose)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)
}