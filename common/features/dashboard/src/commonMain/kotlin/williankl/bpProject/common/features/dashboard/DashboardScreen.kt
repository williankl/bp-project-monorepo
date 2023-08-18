package williankl.bpProject.common.features.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public object DashboardScreen : BeautifulScreen() {

    private val options by lazy {
        DashboardActions.entries.toList()
    }

    @Composable
    override fun BeautifulContent() {
        var currentOption by remember {
            mutableStateOf<DashboardActions?>(DashboardActions.Home)
        }

        DashboardScreenContent(
            currentAction = currentOption,
            onOptionSelected = { selectedAction ->
                currentOption =
                    if (selectedAction == currentOption) null
                    else selectedAction
            },
            modifier = Modifier.fillMaxSize(),
        )
    }

    @Composable
    private fun DashboardScreenContent(
        currentAction: DashboardActions?,
        onOptionSelected: (DashboardActions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

            }

            Row(
                modifier = Modifier
                    .background(BeautifulColor.Surface.composeColor)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                options.forEach { option ->
                    val (tintColor, backgroundColor) = if (option == currentAction) {
                        BeautifulColor.PrimaryHigh to BeautifulColor.SecondaryHigh
                    } else {
                        BeautifulColor.SecondaryHigh to BeautifulColor.Transparent
                    }

                    Image(
                        painter = painterResource(option.icon),
                        colorFilter = ColorFilter.tint(tintColor.composeColor),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .clickableIcon { onOptionSelected(option) }
                            .background(
                                shape = CircleShape,
                                color = backgroundColor.composeColor,
                            )
                    )
                }
            }
        }
    }
}