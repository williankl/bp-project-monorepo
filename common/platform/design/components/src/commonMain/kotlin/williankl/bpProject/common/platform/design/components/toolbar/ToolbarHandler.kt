package williankl.bpProject.common.platform.design.components.toolbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.resources.ImageResource
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor

public class ToolbarHandler {

    public data class ToolbarAction(
        val icon: ImageResource,
        val onClick: () -> Unit,
    )

    public var backgroundColor: BeautifulColor by mutableStateOf(BeautifulColor.Background)
    public var headingIcon: ToolbarAction? by mutableStateOf(null)
    public var trailingIcons: List<ToolbarAction> by mutableStateOf(emptyList())
    public var label: String? by mutableStateOf(null)
    public var visible: Boolean by mutableStateOf(true)
}
