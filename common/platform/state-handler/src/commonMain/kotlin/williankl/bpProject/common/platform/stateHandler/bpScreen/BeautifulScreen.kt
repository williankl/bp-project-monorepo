package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.platform.design.components.toolbar.LocalBeautifulToolbarHandler
import williankl.bpProject.common.platform.design.components.toolbar.ToolbarHandler
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor

public abstract class BeautifulScreen : Screen {

    @Composable
    override fun Content() {
        val toolbarHandler = LocalBeautifulToolbarHandler.current
        val navigator = LocalNavigator.currentOrThrow

        initialToolbarConfig(navigator, toolbarHandler)

        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    private fun ActualScreenContent() {
        BeautifulContent()
    }

    @Composable
    public open fun initialToolbarConfig(
        navigator: Navigator,
        toolbarHandler: ToolbarHandler,
    ) {
        with(toolbarHandler) {
            backgroundColor = BeautifulColor.BackgroundHigh
            trailingIcons = emptyList()
            label = null
            visible = true

            headingIcon =
                if (navigator.canPop) ToolbarHandler.ToolbarAction(
                    icon = SharedDesignCoreResources.images.ic_chevron_left,
                    onClick = { navigator.pop() }
                )
                else null
        }
    }
}
