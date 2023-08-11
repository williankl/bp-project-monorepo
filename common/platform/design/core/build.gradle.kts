import dev.icerock.gradle.MRVisibility

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

android {
    namespace = "williankl.bpProject.common.platform.design.core"
}

dependencies {
    commonMainImplementation(projects.common.core)

    commonMainApi(compose.runtime)
    commonMainApi(compose.foundation)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    commonMainImplementation(libs.moko.resourcesCompose)
}

multiplatformResources {
    multiplatformResourcesPackage = "williankl.bpProject.common.platform.design.core"
    multiplatformResourcesClassName = "SharedDesignCoreResources"
    multiplatformResourcesVisibility = MRVisibility.Public
}