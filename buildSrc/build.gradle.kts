plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

gradlePlugin {
    plugins.register("multiplatform-module") {
        id = "bp.multiplatform"
        implementationClass = "williankl.bpProject.buildSrc.MultiplatformModulePlugin"
    }
    plugins.register("android-app-module") {
        id = "bp.android.app"
        implementationClass = "williankl.bpProject.buildSrc.AndroidAppModulePlugin"
    }
    plugins.register("kotlin-module") {
        id = "bp.kotlin"
        implementationClass = "williankl.bpProject.buildSrc.KotlinModulePlugin"
    }
}

dependencies {
    implementation(libs.plugin.android)
    implementation(libs.plugin.cashApp.sqlDelight)
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.kotlin.serialization)
    implementation(libs.plugin.multiplatform.compose)
    implementation(libs.plugin.ktlint)
    implementation(libs.plugin.detekt)
    implementation(libs.plugin.ksp)
    implementation(libs.plugin.buildKonfig)
    implementation(libs.plugin.google.services)
    implementation(libs.plugin.google.crashlytics)
}