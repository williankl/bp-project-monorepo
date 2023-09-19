import williankl.bpProject.buildSrc.helpers.addJvmTarget

plugins {
    id("bp.multiplatform")
}

android {
    namespace = "williankl.bpProject.common.data.cypher"
}

addJvmTarget()

dependencies {
    commonMainImplementation(libs.kodein.core)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.kotlinx.serialization.json)
}