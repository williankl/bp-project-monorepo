package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.platform.design.components.toolbar.LocalBeautifulToolbarHandler
import williankl.bpProject.common.platform.design.components.toolbar.ToolbarHandler
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources

public abstract class BeautifulScreen : Screen {

    @Composable
    override fun Content() {
        val toolbarHandler = LocalBeautifulToolbarHandler.current
        toolbarHandler.initialToolbarConfig()
        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    private fun ActualScreenContent() {
        BeautifulContent()
    }

    @Composable
    public open fun ToolbarHandler.initialToolbarConfig() {
        val navigator = LocalNavigator.currentOrThrow
        headingIcon =
            if (navigator.canPop) ToolbarHandler.ToolbarAction(
                icon = SharedDesignCoreResources.images.ic_chevron_left,
                onClick = { navigator.pop() }
            )
            else null
    }
}
