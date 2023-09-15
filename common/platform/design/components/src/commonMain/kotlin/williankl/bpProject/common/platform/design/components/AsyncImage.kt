package williankl.bpProject.common.platform.design.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
public fun AsyncImage(
    url: String,
    modifier: Modifier = Modifier,
    onError: @Composable () -> Unit = { /* Nothing by default */ },
    onLoading: @Composable () -> Unit = { /* Nothing by default */ },
    contentAlignment: Alignment = Alignment.Center,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val resource = asyncPainterResource(url)
    KamelImage(
        modifier = modifier,
        resource = resource,
        contentDescription = null,
        onLoading = { _ -> onLoading() },
        onFailure = { _ -> onError() },
        contentAlignment = contentAlignment,
        alignment = alignment,
        contentScale = contentScale,
    )
}