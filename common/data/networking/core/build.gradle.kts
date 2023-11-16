import williankl.bpProject.buildSrc.helpers.addJvmTarget

plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.networking.core"
}

addJvmTarget()

dependencies {
    commonMainImplementation(projects.common.core)

    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.ktor.client.logging)
    commonMainImplementation(libs.ktor.client.contentNegotiation)
    commonMainImplementation(libs.ktor.client.jsonSerialization)
    commonMainImplementation(libs.kotlinx.serialization.json)
    commonMainImplementation(libs.kotlinx.serialization.core)
}