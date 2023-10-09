package williankl.bpProject.common.platform.design.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

public data class CommentBubbleAction(
    val icon: ImageResource,
    val onClick: () -> Unit,
)

@Composable
public fun CommentBubble(
    rating: PlaceRating,
    modifier: Modifier = Modifier,
    actions: List<CommentBubbleAction> = emptyList(),
    endAction: CommentBubbleAction? = null,
) {
    val strings = LocalComponentsStrings.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = BeautifulColor.Primary.composeColor,
                shape = BeautifulShape.Rounded.Regular.composeShape,
            )
            .padding(6.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier,
        ) {
            AsyncImage(
                url = rating.ownerData.avatarUrl.orEmpty(),
                onError = {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_profile),
                        colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Circle.composeShape)
                    .size(30.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = rating.ownerData.tag ?: rating.ownerData.name,
                    size = TextSize.Small,
                    weight = FontWeight.SemiBold,
                )

                StarRating(
                    rating = rating.rating.toFloat(),
                    starSize = 8.dp
                )
            }

            Text(
                text = strings.bubbleStrings.timePassed(rating.updatedAt ?: rating.createdAt),
                size = TextSize.Small,
                modifier = Modifier,
            )
        }

        if (rating.comment != null) {
            Text(
                text = rating.comment.orEmpty(),
                modifier = Modifier.align(Alignment.Start),
            )
        }

        AnimatedVisibility(
            visible = actions.isNotEmpty() || endAction != null
        ) {
            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .width(60.dp)
                    .height(1.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 6.dp),
            ) {
                actions.forEach { action ->
                    Image(
                        painter = painterResource(action.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon(padding = 0.dp) { action.onClick() }
                            .size(24.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                if (endAction != null) {
                    Image(
                        painter = painterResource(endAction.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon(padding = 0.dp) { endAction.onClick() }
                            .size(24.dp)
                    )
                }
            }
        }
    }
}
