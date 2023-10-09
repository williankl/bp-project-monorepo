import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "williankl.bpProject.common.platform.design.components"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.platform.design.components")

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(libs.moko.resourcesCompose)
    commonMainImplementation(libs.kamel)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)

    androidMainImplementation(libs.maps.android.compose)
    androidMainImplementation(libs.maps.android.composeUtils)
}