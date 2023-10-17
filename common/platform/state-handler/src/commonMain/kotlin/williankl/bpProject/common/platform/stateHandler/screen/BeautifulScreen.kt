package williankl.bpProject.common.platform.stateHandler.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.stateHandler.LocalToolbarConfig
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import williankl.bpProject.common.platform.stateHandler.UIState
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.BeautifulToolbar
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public abstract class BeautifulScreen : Screen {

    public var state: UIState by mutableStateOf(UIState.Content)
        private set

    public open val toolbarConfig: ToolbarConfig
        @Composable get() {
            val navigator = LocalNavigator.currentOrThrow
            return remember {
                ToolbarConfig(
                    headingIcon =
                    if (navigator.canPop) {
                        ToolbarConfig.ToolbarAction(
                            icon = SharedDesignCoreResources.images.ic_chevron_left,
                            onClick = { navigator.pop() }
                        )
                    } else null
                )
            }
        }

    @Composable
    override fun Content() {
        val hasToolbarContent = toolbarConfig.label != null ||
                toolbarConfig.headingIcon != null ||
                toolbarConfig.trailingIcons.isNotEmpty()

        Column {
            AnimatedVisibility(
                visible = hasToolbarContent,
            ) {
                BeautifulToolbar(
                    label = toolbarConfig.label,
                    headingIcon = toolbarConfig.headingIcon,
                    backgroundColor = toolbarConfig.backgroundColor,
                    trailingIcons = toolbarConfig.trailingIcons,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            CompositionLocalProvider(
                LocalToolbarConfig provides toolbarConfig
            ) {
                BeautifulContent()

                AnimatedContent(
                    targetState = state,
                    modifier = Modifier.fillMaxSize(),
                    content = { uiState ->
                        when (uiState) {
                            is UIState.Content -> Unit
                            is UIState.Error -> ErrorScreen(
                                reason = uiState.reason,
                                modifier = Modifier.fillMaxSize()
                            )

                            is UIState.Loading -> LoadingScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                )
            }
        }
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    protected fun <T> RunnerModel<T>.collectData(): State<T> {
        val uiState by uiState.collectAsState()
        val data by currentData.collectAsState()

        LaunchedEffect(uiState) {
            state = uiState
        }

        return derivedStateOf { data }
    }

    public fun resetState() {
        state = UIState.Content
    }
}
