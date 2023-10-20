package williankl.bpProject.common.platform.stateHandler.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.nonClickable

@Composable
public fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    val animatedAlpha = remember { Animatable(0f) }
    val alpha by animatedAlpha.asState()

    LaunchedEffect(animatedAlpha) {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(BeautifulColor.Background.composeColor)
            .nonClickable()
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(SharedDesignCoreResources.images.bp_logo_light_bold),
            contentDescription = null,
            modifier = Modifier
                .alpha(alpha)
                .size(80.dp)
        )
    }
}
