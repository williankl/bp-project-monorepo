package williankl.bpProject.common.features.authentication.models

import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources

internal enum class SocialLoginProvider(
    val resource: ImageResource,
) {
    Gmail(
        resource = SharedDesignCoreResources.images.google_logo,
    ),

    Facebook(
        resource = SharedDesignCoreResources.images.facebook_logo,
    )
}
