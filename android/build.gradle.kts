plugins {
    id("bp.android.app")
    id("org.jetbrains.compose")
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
    implementation(projects.common.data.imageRetrievalService)
    implementation(projects.common.data.placeService)

    implementation(projects.common.features.authentication)
    implementation(projects.common.features.dashboard)
    implementation(projects.common.features.places)

    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)
    implementation(compose.animation)
    implementation(libs.android.compose.activity)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.kodein.android)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.korlibs.korim)
    implementation(libs.android.exifInterface)
}