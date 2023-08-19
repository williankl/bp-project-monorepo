package williankl.bpProject.common.features.dashboard

import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources

internal enum class DashboardActions(
    val icon: ImageResource
) {
    Home(SharedDesignCoreResources.images.ic_home),
    Add(SharedDesignCoreResources.images.ic_add_with_circle),
    Profile(SharedDesignCoreResources.images.ic_profile)
}
