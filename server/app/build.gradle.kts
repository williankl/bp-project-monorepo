plugins {
    id("bp.kotlin")
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.data.placeService)
    implementation(projects.server.database)

    implementation(libs.kodein.core)
    implementation(libs.uuid)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.logBack)
}