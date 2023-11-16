plugins {
    id("bp.multiplatform")
    kotlin("plugin.serialization")
}

android {
    namespace = "williankl.bpProject.common.data.auth"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(projects.common.data.cypher)
    commonMainImplementation(projects.common.data.networking.core)

    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.serialization.json)
}