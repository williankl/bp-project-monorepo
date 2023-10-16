plugins {
    id("bp.kotlin")
}

dependencies {
    implementation(projects.server.core)
    implementation(projects.server.routing.core)
}