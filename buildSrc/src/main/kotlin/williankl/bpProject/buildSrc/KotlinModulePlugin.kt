package williankl.bpProject.buildSrc

import org.gradle.api.Plugin
import org.gradle.api.Project
import williankl.bpProject.buildSrc.helpers.applyCommonPlugins
import williankl.bpProject.buildSrc.helpers.applyKotlinOptions
import williankl.bpProject.buildSrc.helpers.applyRepositories

internal class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.jvm")

            applyCommonPlugins()
            applyKotlinOptions()
            applyRepositories()
        }
    }
}