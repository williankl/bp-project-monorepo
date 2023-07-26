package williankl.bpProject.buildSrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import williankl.bpProject.buildSrc.helpers.applyCommonPlugins
import williankl.bpProject.buildSrc.helpers.applyKotlinOptions
import williankl.bpProject.buildSrc.helpers.applyRepositories
import williankl.bpProject.buildSrc.helpers.setupMultiplatformTargets

internal class MultiplatformModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.multiplatform")
            plugins.apply("com.android.library")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
            setupMultiplatformTargets()
        }
    }
}