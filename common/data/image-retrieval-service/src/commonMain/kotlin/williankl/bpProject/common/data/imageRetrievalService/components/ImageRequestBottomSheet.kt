package williankl.bpProject.common.data.imageRetrievalService.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.data.imageRetrievalService.LocalImageRetrievalServiceStrings
import williankl.bpProject.common.platform.design.core.ComposeString
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

internal class ImageRequestBottomSheet(
    private val onOptionSelected: (ImageRequestOptions) -> Unit,
) : BeautifulScreen() {

    internal enum class ImageRequestOptions(
        val label: ComposeString,
        val icon: ImageResource,
    ) {
        ImportFromGallery(
            label = { LocalImageRetrievalServiceStrings.current.importLabel },
            icon = SharedDesignCoreResources.images.ic_import,
        ),
        Camera(
            label = { LocalImageRetrievalServiceStrings.current.captureLabel },
            icon = SharedDesignCoreResources.images.ic_camera,
        ),
    }

    private val options by lazy {
        ImageRequestOptions.entries.toList()
    }

    @Composable
    override fun BeautifulContent() {
        ImageRequestBottomSheetContent(
            onOptionSelected = onOptionSelected,
            modifier = Modifier,
        )
    }

    @Composable
    private fun ImageRequestBottomSheetContent(
        onOptionSelected: (ImageRequestOptions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clickable { onOptionSelected(option) }
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(option.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                        modifier = Modifier.size(24.dp),
                    )

                    Text(
                        text = option.label(),
                        color = BeautifulColor.NeutralHigh,
                        weight = FontWeight.SemiBold,
                    )
                }

                if (index != options.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .background(BeautifulColor.Surface.composeColor)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }
        }
    }
}
