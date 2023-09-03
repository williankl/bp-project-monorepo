import java.util.*
import williankl.bpProject.buildSrc.helpers.buildConfigField
import williankl.bpProject.buildSrc.helpers.fromLocalProperties

plugins {
    id("bp.android.app")
    id("org.jetbrains.compose")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "williankl.bpProject.android"

    defaultConfig {
        buildFeatures.buildConfig = true
        manifestPlaceholders["MAPS_API_KEY"] = fromLocalProperties("MAPS_API_KEY")
        buildConfigField("MAPS_API_KEY", fromLocalProperties("MAPS_API_KEY"))
    }
}

dependencies {
    implementation(projects.common.core)

    implementation(projects.common.platform.design.core)
    implementation(projects.common.platform.stateHandler)

    implementation(projects.common.data.firebaseIntegration)
    implementation(projects.common.data.imageRetrievalService)
    implementation(projects.common.data.networking)
    implementation(projects.common.data.auth)
    implementation(projects.common.data.placeService.core)
    implementation(projects.common.data.placeService.infrastructure)

    implementation(projects.common.features.authentication)
    implementation(projects.common.features.dashboard)
    implementation(projects.common.features.places)

    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)
    implementation(compose.animation)
    implementation(compose.material)
    implementation(libs.android.compose.activity)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.kodein.android)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.bottomSheetNavigator)
    implementation(libs.voyager.transitions)
}