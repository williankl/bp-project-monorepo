plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.core"
}

dependencies {
    commonMainImplementation(libs.uuid)
}