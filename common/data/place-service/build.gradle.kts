plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.placeService"
}

dependencies {
    commonMainImplementation(projects.common.core)
    androidMainImplementation(libs.firebase.firestore)
}