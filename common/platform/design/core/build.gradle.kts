plugins {
    id("bp.multiplatform")
    id("org.jetbrains.compose")
}

android {
    namespace = "williankl.bpProject.common.platform.design.core"
}

dependencies {
    commonMainImplementation(projects.common.core)

    commonMainApi(compose.runtime)
    commonMainApi(compose.foundation)
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)

    commonMainImplementation(libs.voyager.navigator)
}