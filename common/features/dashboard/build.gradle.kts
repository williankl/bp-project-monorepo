import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.features.dashboard"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.features.dashboard")

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.design.components)
    commonMainImplementation(projects.common.data.auth)
    commonMainImplementation(projects.common.data.sessionHandler)
    commonMainImplementation(projects.common.data.imageRetrievalService)
    commonMainImplementation(projects.common.platform.stateHandler)
    commonMainImplementation(projects.common.features.places)
    commonMainImplementation(libs.moko.resourcesCompose)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)
}