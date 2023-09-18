package williankl.bpProject.common.platform.stateHandler.screen.toolbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig.ToolbarAction

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun BeautifulToolbar(
    modifier: Modifier = Modifier,
    label: String? = null,
    headingIcon: ToolbarAction? = null,
    backgroundColor: BeautifulColor = BeautifulColor.BackgroundHigh,
    trailingIcons: List<ToolbarAction> = emptyList(),
) {
    Box(
        modifier = modifier
            .background(backgroundColor.composeColor)
            .padding(
                vertical = 14.dp,
                horizontal = 20.dp,
            ),
    ) {
        AnimatedContent(
            targetState = headingIcon != null,
            transitionSpec = { fadeIn() + expandHorizontally() with fadeOut() + shrinkHorizontally() },
            modifier = Modifier.align(Alignment.CenterStart),
        ) { shouldShowHeadingIcon ->
            if (shouldShowHeadingIcon && headingIcon != null) {
                Image(
                    painter = painterResource(headingIcon.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .clickableIcon { headingIcon.onClick() }
                        .size(20.dp)
                )
            }
        }

        val bias = if (headingIcon != null) 0f else -1f
        val animatedBias by animateFloatAsState(bias)
        val alignment = BiasAlignment(
            horizontalBias = animatedBias,
            verticalBias = 0f,
        )

        AnimatedContent(
            targetState = label.orEmpty(),
            transitionSpec = { fadeIn() with fadeOut() },
            modifier = Modifier.align(alignment),
        ) { toolbarLabel ->
            Text(
                text = toolbarLabel,
                color = BeautifulColor.NeutralHigh,
                weight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                size =
                if (headingIcon == null) TextSize.XXLarge
                else TextSize.Regular,
            )
        }

        AnimatedContent(
            targetState = trailingIcons,
            transitionSpec = { fadeIn() with fadeOut() },
            modifier = Modifier.align(Alignment.CenterEnd),
        ) { shownIcons ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                shownIcons.forEach { icon ->
                    Image(
                        painter = painterResource(icon.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon { icon.onClick() }
                            .size(20.dp)
                    )
                }
            }
        }
    }
}
