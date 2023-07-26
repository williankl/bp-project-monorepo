plugins {
    id("bp.android.app")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "williankl.bpProject.android"
}

dependencies {
    implementation(libs.android.compose.activity)
    implementation(libs.android.compose.ui)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}