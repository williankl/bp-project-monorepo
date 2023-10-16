plugins {
    id("bp.kotlin")
    id("io.ktor.plugin")
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.data.cypher)
    implementation(projects.common.data.networking)
    implementation(projects.common.data.placeService.core)
    implementation(projects.common.data.placeService.infrastructure)

    implementation(projects.server.core)
    implementation(projects.server.routing.core)
    implementation(projects.server.routing.bff)
    implementation(projects.server.database)

    implementation(libs.kodein.core)
    implementation(libs.kmm.uuid)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.authentication)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.logback)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}