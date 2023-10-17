package williankl.bpProject.common.platform.design.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize

@Composable
public fun TextContainer(
    text: String,
    modifier: Modifier = Modifier,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    size: TextSize = TextSize.Regular,
    style: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    decoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    preOverflowMaxLines: Int = Int.MAX_VALUE,
    afterOverflowMaxLines: Int = Int.MAX_VALUE,
    ignoreMarkdown: Boolean = false,
) {
    var shouldExpand by remember {
        mutableStateOf(false)
    }

    var didOverFlow by remember {
        mutableStateOf(false)
    }

    val actionDegree =
        if (shouldExpand) 0f
        else 180f

    val animatedActionDegree by animateFloatAsState(actionDegree)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .border(
                color = BeautifulColor.Primary.composeColor,
                shape = BeautifulShape.Rounded.Regular.composeShape,
                width = 1.dp,
            )
            .padding(14.dp),
    ) {
        Text(
            text = text,
            color = color,
            size = size,
            style = style,
            weight = weight,
            decoration = decoration,
            textAlign = textAlign,
            overflow = overflow,
            maxLines = if (shouldExpand) afterOverflowMaxLines else preOverflowMaxLines,
            ignoreMarkdown = ignoreMarkdown,
            onTextLayout = { result -> didOverFlow = result.didOverflowHeight || result.didOverflowWidth },
        )

        AnimatedVisibility(
            visible = didOverFlow
        ) {
            if (didOverFlow) {
                Image(
                    painter = painterResource(SharedDesignCoreResources.images.ic_chevron_down),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                    modifier = Modifier
                        .clickableIcon { shouldExpand = shouldExpand.not() }
                        .rotate(animatedActionDegree)
                        .size(20.dp)
                )
            }
        }
    }
}
