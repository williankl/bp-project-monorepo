plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.placeService"
}

dependencies {
    commonMainImplementation(projects.common.core)
    commonMainImplementation(libs.uuid)

    androidMainImplementation(platform(libs.firebase.bom))
    androidMainImplementation(libs.firebase.firestore)
}