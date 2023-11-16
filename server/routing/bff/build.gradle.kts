plugins {
    id("bp.kotlin")
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.data.placeService.core)
    implementation(projects.server.database)
    implementation(projects.server.core)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.authentication)
    implementation(libs.kodein.core)
}