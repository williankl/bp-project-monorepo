package williankl.bpProject.common.platform.stateHandler.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.LocalToolbarConfig
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import williankl.bpProject.common.platform.stateHandler.models.ModelState
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.BeautifulToolbar
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public abstract class BeautifulScreen : Screen {

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
        val router = LocalRouter.currentOrThrow
        val modelState by router.state.collectAsState()
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    BeautifulContent()

                    AnimatedContent(
                        targetState = modelState,
                        modifier = Modifier,
                        content = { uiState ->
                            when (uiState) {
                                is ModelState.Content -> Unit
                                is ModelState.Error -> ErrorScreen(
                                    reason = uiState.reason,
                                    modifier = Modifier.fillMaxSize()
                                )

                                is ModelState.Loading -> LoadingScreen(
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    protected fun <T> RunnerModel<T>.collectData(): State<T> {
        val router = LocalRouter.currentOrThrow
        val modelUIState by currentUIState.collectAsState()

        LaunchedEffect(modelUIState.modelState) {
            router.updateUIState(modelUIState.modelState)
        }

        return remember {
            derivedStateOf { modelUIState.content }
        }
    }
}
