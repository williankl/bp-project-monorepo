plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.core"
}

dependencies {
    commonMainImplementation(libs.kmm.uuid)
    commonMainImplementation(libs.kmm.uri)
    commonMainImplementation(libs.kmm.bitmap)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(compose.foundation)

    androidMainImplementation(libs.android.exifInterface)
    androidMainImplementation(libs.android.androidx.core)
}