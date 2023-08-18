plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.data.imageRetrievalService"
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.korim.core)
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
}