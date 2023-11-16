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
include(":server:core")
include(":server:database")
include(":server:routing")
include(":server:routing:core")
include(":server:routing:bff")

include(":common")
include(":common:application")
include(":common:core")

include(":common:data")
include(":common:data:firebase-integration")
include(":common:data:image-retrieval-service")
include(":common:data:cypher")
include(":common:data:auth")
include(":common:data:session-handler")

include(":common:data:networking")
include(":common:data:networking:core")
include(":common:data:networking:internal")

include(":common:data:place-service")
include(":common:data:place-service:core")
include(":common:data:place-service:device-location")
include(":common:data:place-service:infrastructure")

include(":common:platform")
include(":common:platform:uri-navigator")
include(":common:platform:design")
include(":common:platform:design:core")
include(":common:platform:design:components")
include(":common:platform:state-handler")

include(":common:features")
include(":common:features:places")
include(":common:features:dashboard")
include(":common:features:authentication")