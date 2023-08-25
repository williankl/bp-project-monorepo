plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.data.imageRetrievalService"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.platform.design.core)
    commonMainImplementation(projects.common.platform.stateHandler)

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)

    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.moko.resourcesCompose)

    androidMainImplementation(libs.android.exifInterface)
    androidMainImplementation(libs.android.androidx.core)
}