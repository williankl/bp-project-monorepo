import dev.icerock.gradle.MRVisibility

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

android {
    namespace = "williankl.bpProject.common.features.authentication"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)
    commonMainImplementation(libs.voyager.navigator)
    commonMainImplementation(libs.moko.resourcesCompose)
}

multiplatformResources {
    multiplatformResourcesPackage = "williankl.bpProject.common.features.authentication"
    multiplatformResourcesClassName = "SharedAuthenticationResources"
    multiplatformResourcesVisibility = MRVisibility.Internal
}