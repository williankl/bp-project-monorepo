package williankl.bpProject.common.features.dashboard.pages.profile.options.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.dashboard.LocalDashboardStrings
import williankl.bpProject.common.features.dashboard.pages.profile.options.menu.MenuSidebarRunnerModel.MenuSidebarPresentation
import williankl.bpProject.common.platform.design.components.ComposableString
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal object MenuSidebarScreen : BeautifulScreen() {

    internal enum class SidebarOptions(
        val label: ComposableString,
        val icon: ImageResource,
    ) {
        Edit(
            label = { LocalDashboardStrings.current.profileStrings.menuStrings.editLabel },
            icon = SharedDesignCoreResources.images.ic_pen_and_note,
        ),

        Settings(
            label = { LocalDashboardStrings.current.profileStrings.menuStrings.settingsLabel },
            icon = SharedDesignCoreResources.images.ic_cog,
        ),

        Support(
            label = { LocalDashboardStrings.current.profileStrings.menuStrings.supportLabel },
            icon = SharedDesignCoreResources.images.ic_help_circle,
        ),

        Exit(
            label = { LocalDashboardStrings.current.profileStrings.menuStrings.exitLabel },
            icon = SharedDesignCoreResources.images.ic_exit,
        ),
    }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<MenuSidebarRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        MenuSidebaseContent(
            presentation = presentation,
            onOptionSelected = {
                /* todo - handle selection */
            },
            modifier = Modifier
                .background(BeautifulColor.BackgroundHigh.composeColor)
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
        )
    }

    @Composable
    private fun MenuSidebaseContent(
        presentation: MenuSidebarPresentation,
        onOptionSelected: (SidebarOptions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            Column(

            ) {

            }

            SidebarOptions.entries.forEach { option ->
                MenuOption(
                    label = option.label(),
                    icon = option.icon,
                    modifier = Modifier
                        .clickable { onOptionSelected(option) }
                        .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    private fun MenuOption(
        label: String,
        icon: ImageResource,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 12.dp,
                )
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                )

                Text(
                    text = label,
                    weight = FontWeight.Bold,
                    size = TextSize.Large,
                )
            }

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}