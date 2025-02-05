import dev.icerock.gradle.MRVisibility
import williankl.bpProject.buildSrc.helpers.applyCommonMainCodeGeneration
import williankl.bpProject.buildSrc.helpers.applyMultiModuleKsp
import williankl.bpProject.buildSrc.helpers.commonMainLyricistImplementation

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.google.devtools.ksp")
}

android {
    namespace = "williankl.bpProject.common.features.authentication"
}

applyCommonMainCodeGeneration()
applyMultiModuleKsp("williankl.bpProject.common.features.authentication")

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.auth)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)
    commonMainImplementation(projects.common.data.sessionHandler)
    commonMainImplementation(libs.moko.resourcesCompose)
    commonMainImplementation(libs.kodein.core)

    commonMainImplementation(libs.lyricist.core)
    commonMainLyricistImplementation(libs.lyricist.ksp)
}

multiplatformResources {
    multiplatformResourcesPackage = "williankl.bpProject.common.features.authentication"
    multiplatformResourcesClassName = "SharedAuthenticationResources"
    multiplatformResourcesVisibility = MRVisibility.Internal
}