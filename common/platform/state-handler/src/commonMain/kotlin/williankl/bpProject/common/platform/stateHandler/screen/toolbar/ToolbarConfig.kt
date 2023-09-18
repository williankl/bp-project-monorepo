package williankl.bpProject.common.platform.stateHandler.screen.toolbar

import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor

public data class ToolbarConfig(
    val backgroundColor: BeautifulColor = BeautifulColor.BackgroundHigh,
    val headingIcon: ToolbarHandler.ToolbarAction? = null,
    val trailingIcons: List<ToolbarAction> = emptyList(),
    val label: String? = null,
) {
    public data class ToolbarAction(
        val icon: ImageResource,
        val onClick: () -> Unit,
    )
}