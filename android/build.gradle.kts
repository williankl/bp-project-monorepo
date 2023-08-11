plugins {
    id("bp.android.app")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "williankl.bpProject.android"
}

dependencies {
    implementation(projects.common.core)

    implementation(projects.common.platform.design.core)
    implementation(projects.common.platform.stateHandler)

    implementation(projects.common.data.networking)
    implementation(projects.common.data.auth)
    implementation(projects.common.data.placeService)

    implementation(projects.common.features.authentication)
    implementation(projects.common.features.places)

    implementation(libs.android.compose.activity)
    implementation(libs.android.compose.ui)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.kodein.android)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
}