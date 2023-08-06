plugins {
    id("bp.kotlin")
    id("app.cash.sqldelight")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(libs.cashApp.jdbcDriver)
    implementation(libs.cashApp.sqliteDriver)
    implementation(libs.hikari)
    implementation(libs.postgres)
    implementation(libs.kodein.core)
}

sqldelight {
    databases {
        create("BpProject") {
            packageName.set("williankl.bpProject.server.database")
            dialect(libs.cashApp.postgresDialict)
        }
    }
}