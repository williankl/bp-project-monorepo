plugins {
    id("bp.kotlin")
    id("app.cash.sqldelight")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(projects.common.core)

    implementation(libs.cashApp.jdbcDriver)
    implementation(libs.hikari)
    implementation(libs.postgres)
    implementation(libs.kodein.core)
    implementation(libs.uuid)
}

sqldelight {
    databases {
        create("BpProject") {
            packageName.set("williankl.bpProject.server.database")
            dialect(libs.cashApp.postgresDialict)
        }
    }
}