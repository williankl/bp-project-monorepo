plugins {
    id("bp.kotlin")
    id("app.cash.sqldelight")
}

dependencies {
    implementation(libs.cashApp.jdbcDriver)
}

sqldelight {
    databases {
        create("bp-project") {
            srcDirs("bp-database")
            packageName.set("williankl.bpProject.server.database")
            dialect(libs.cashApp.postgresDialict)
        }
    }
}