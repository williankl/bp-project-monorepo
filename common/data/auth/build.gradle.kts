plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.auth"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.ktor.client.core)
}