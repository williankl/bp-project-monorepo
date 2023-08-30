import dev.icerock.gradle.MRVisibility
import williankl.bpProject.buildSrc.helpers.addJvmTarget

plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.google.devtools.ksp")
}

android {
    namespace = "williankl.bpProject.common.platform.design.core"
}

addJvmTarget()

dependencies {
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