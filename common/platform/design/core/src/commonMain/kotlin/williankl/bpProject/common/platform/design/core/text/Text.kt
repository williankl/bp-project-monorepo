package williankl.bpProject.common.platform.design.core.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import williankl.bpProject.common.platform.design.core.colors.BeautifulBrush
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.retrieveBPFontFamily

@Composable
public fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    size: TextSize = TextSize.Regular,
    style: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    decoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    ignoreMarkdown: Boolean = false,
) {
    CoreText(
        text = when {
            ignoreMarkdown -> AnnotatedString(text)
            color is BeautifulBrush -> applyMarkdown(text, color)
            else -> applyMarkdown(text)
        },
        modifier = modifier,
        color = when (color) {
            is BeautifulColor -> color
            else -> BeautifulColor.NeutralHigh
        },
        size = size,
        style = style,
        weight = weight,
        decoration = decoration,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
internal fun CoreText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: BeautifulColor = BeautifulColor.NeutralHigh,
    size: TextSize = TextSize.Regular,
    style: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    decoration: TextDecoration = TextDecoration.None,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color.composeColor,
        fontSize = size.textUnit,
        fontStyle = style,
        fontWeight = weight,
        textDecoration = decoration,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        softWrap = true,
        onTextLayout = { /* Nothing */ },
        style = LocalTextStyle.current,
        letterSpacing = TextUnit.Unspecified,
        fontFamily = retrieveBPFontFamily(),
        lineHeight = TextUnit.Unspecified,
    )
}
