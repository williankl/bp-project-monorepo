package williankl.bpProject.buildSrc.helpers

import com.android.build.api.dsl.VariantDimension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.io.FileInputStream
import java.util.Properties


public fun Project.setupLyricist() {
    configure<KspExtension> {
        arg("lyricist.internalVisibility", "true")
        arg("lyricist.packageName", "williankl.bpProject")
        arg("lyricist.moduleName", project.name)
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
public inline fun <reified ValueT> VariantDimension.buildConfigField(
    name: String,
    value: ValueT
) {
    val resolvedValue = when (value) {
        is String -> "\"$value\""
        else -> value
    }.toString()
    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}

internal fun Project.applyRepositories() {
    repositories.apply {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io/")
        maven("https://repo.repsy.io/mvn/chrynan/public")
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

internal fun Project.applyCommonPlugins() {
    plugins.apply("org.jmailen.kotlinter")
    plugins.apply("io.gitlab.arturbosch.detekt")
}

internal fun Project.applyKotlinOptions() {
    applyCodeSafetyFeatures(true)

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "${JavaVersion.VERSION_17}"
            freeCompilerArgs += "-Xcontext-receivers"
        }
    }
}

internal fun Project.retrieveVersionFromCatalogs(key: String): String {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    return libs.findVersion(key).get().requiredVersion
}
