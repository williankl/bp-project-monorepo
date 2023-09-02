plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.platform.design.components"
}

dependencies {
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    commonMainImplementation(libs.atomicfu)
    commonMainImplementation(libs.moko.resourcesCompose)

    androidMainImplementation(libs.maps.android.compose)
    androidMainImplementation(libs.maps.android.composeUtils)
}