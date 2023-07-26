package williankl.bpProject.buildSrc.helpers

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import williankl.bpProject.buildSrc.helpers.findAndroidExtension
import williankl.bpProject.buildSrc.helpers.setupAndroid

public fun DependencyHandlerScope.commonMainLyricistImplementation(
    lyricistDependency: Provider<MinimalExternalModuleDependency>
) {
    val actualDependency = lyricistDependency.get()
    add(
        configurationName = "kspCommonMainMetadata",
        dependencyNotation = "${actualDependency.module}:${actualDependency.version}"
    )
}

public fun Project.applyMultiModuleKsp(packageName: String) {
    configure<KspExtension> {
        arg("lyricist.packageName", packageName)
        arg("lyricist.moduleName", project.name)
        arg("lyricist.internalVisibility", "true")
    }
}

public fun Project.applyCommonMainCodeGeneration() {
    tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }

    configure<KotlinMultiplatformExtension> {
        sourceSets.getByName("commonMain") {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}

internal fun Project.setupMultiplatformTargets() {
    applyAndroidTarget()
    applyIOSTarget()
}

private fun Project.applyAndroidTarget() {
    findAndroidExtension().apply {
        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        setupAndroid()
    }

    extensions.configure<KotlinMultiplatformExtension>() {
        androidTarget()
    }
}

private fun Project.applyIOSTarget() {
    extensions.configure<KotlinMultiplatformExtension>() {
        ios()
    }
}