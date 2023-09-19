plugins {
    id("bp.kotlin")
    id("io.ktor.plugin")
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.data.placeService.core)
    implementation(projects.common.data.cypher)
    implementation(projects.server.database)

    implementation(libs.kodein.core)
    implementation(libs.kmm.uuid)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.authentication)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}