plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.networking"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.ktor.client.logging)
    commonMainImplementation(libs.ktor.client.contentNegotiation)
    commonMainImplementation(libs.ktor.client.jsonSerialization)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)

    androidMainImplementation(libs.ktor.client.okHttp)
    jvmMainImplementation(libs.ktor.client.okHttp)
    iosMainImplementation(libs.ktor.client.darwin)
}