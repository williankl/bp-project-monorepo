plugins {
    id("bp.kotlin")
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.data.cypher)
    implementation(projects.server.database)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.authentication)
}