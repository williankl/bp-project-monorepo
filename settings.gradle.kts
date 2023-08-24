enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io/")
        mavenCentral()
        google()
    }
}

rootProject.name = "bp-project"

include(":android")
include(":server")
include(":server:app")
include(":server:database")

include(":common")
include(":common:core")

include(":common:data")
include(":common:data:networking")
include(":common:data:auth")
include(":common:data:image-retrieval-service")
include(":common:data:place-service")

include(":common:platform")
include(":common:platform:design")
include(":common:platform:design:core")
include(":common:platform:state-handler")

include(":common:features")
include(":common:features:places")
include(":common:features:dashboard")
include(":common:features:authentication")