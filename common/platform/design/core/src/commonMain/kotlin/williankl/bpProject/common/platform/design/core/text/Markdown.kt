package williankl.bpProject.common.platform.design.core.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import williankl.bpProject.common.platform.design.core.colors.BeautifulBrush
import williankl.bpProject.common.platform.design.core.colors.composeColor

internal data class SpanEraseData(
    val start: Int,
    val end: Int,
)

internal sealed class Markdown(
    val pattern: Regex,
    val spanStyle: SpanStyle,
    val eraseData: SpanEraseData,
) {
    data object UnderLine : Markdown(
        pattern = Regex("_(.*?)_"),
        spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
        eraseData = SpanEraseData(
            start = 1,
            end = 1,
        ),
    )

    data object Bold : Markdown(
        pattern = Regex("\\*(.*?)\\*"),
        spanStyle = SpanStyle(fontWeight = FontWeight.Bold),
        eraseData = SpanEraseData(
            start = 1,
            end = 1,
        ),
    )

    data object SemiBold : Markdown(
        pattern = Regex("\\*\\*(.*?)\\*\\*"),
        spanStyle = SpanStyle(fontWeight = FontWeight.SemiBold),
        eraseData = SpanEraseData(
            start = 1,
            end = 1,
        ),
    )

    data object Italic : Markdown(
        pattern = Regex("/(.*?)/"),
        spanStyle = SpanStyle(fontStyle = FontStyle.Italic),
        eraseData = SpanEraseData(
            start = 1,
            end = 1,
        ),
    )
}

@Composable
@OptIn(ExperimentalTextApi::class)
internal fun applyMarkdown(
    string: String,
    brush: BeautifulBrush? = null
): AnnotatedString {
    val markdowns = listOf(
        Markdown.UnderLine,
        Markdown.Bold,
        Markdown.SemiBold,
        Markdown.Italic,
    )

    var modifiedText = string
    val markdownRanges = mutableListOf<AnnotatedString.Range<Markdown>>()

    markdowns.forEach { markdown ->
        val matches = markdown.pattern.findAll(string)
        matches.forEach { match ->
            markdownRanges += AnnotatedString.Range(
                item = markdown,
                start = match.range.first,
                end = match.range.last,
            )
        }
    }

    val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()

    if (brush != null) {
        spanStyles += AnnotatedString.Range(
            item = SpanStyle(
                brush = Brush.linearGradient(
                    brush.colors.map { color -> color.composeColor }
                )
            ),
            start = 0,
            end = string.length
        )
    }

    markdownRanges
        .forEach { markdownRange ->
            modifiedText = StringBuilder(modifiedText).apply {
                repeat(markdownRange.item.eraseData.start) { padding ->
                    set(markdownRange.start + padding, Char.MIN_VALUE)
                }

                repeat(markdownRange.item.eraseData.end) { padding ->
                    set(markdownRange.end - padding, Char.MIN_VALUE)
                }
            }.toString()

            spanStyles += AnnotatedString.Range(
                item = markdownRange.item.spanStyle,
                start = markdownRange.start,
                end = markdownRange.end,
            )
        }

    return AnnotatedString(modifiedText, spanStyles)
}
