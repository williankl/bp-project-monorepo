package williankl.bpProject.common.platform.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.modifyIf

@Composable
public fun StarRating(
    rating: Float,
    modifier: Modifier = Modifier,
    onRating: ((Int) -> Unit)? = null,
    starSize: Dp = 24.dp,
    spacing: Dp = 6.dp,
    starCount: Int = 5,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        repeat(starCount) { index ->
            val paddedIndex = index + 1
            val resource = when {
                rating < paddedIndex && rating > index -> SharedDesignCoreResources.images.ic_star_half
                rating >= paddedIndex -> SharedDesignCoreResources.images.ic_star_full
                else -> SharedDesignCoreResources.images.ic_star
            }

            Image(
                painter = painterResource(resource),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier
                    .modifyIf(onRating != null) {
                        clickableIcon(padding = 0.dp) { onRating?.invoke(paddedIndex) }
                    }
                    .size(starSize)
            )
        }
    }
}
