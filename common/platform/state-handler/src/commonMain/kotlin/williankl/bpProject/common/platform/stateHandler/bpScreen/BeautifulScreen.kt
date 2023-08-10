package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import williankl.bpProject.common.platform.stateHandler.RunnerModel
import williankl.bpProject.common.platform.stateHandler.UIState

public abstract class BeautifulScreen : Screen {

    public var screenState: UIState by mutableStateOf(UIState.Content)
    override fun Content() {
        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    protected inline fun <R, reified T : RunnerModel<R>> rememberRunnerModel(): T {
        val model = rememberScreenModel<T>()
        val modelUiState by model.uiState.collectAsState()
        LaunchedEffect(modelUiState) {
            screenState = modelUiState
        }
        return model
    }

    @Composable
    private fun ActualScreenContent() {
        BeautifulContent()

        when (val state = screenState) {
            is UIState.Error -> {
                ErrorScreen(
                    reason = state.reason,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is UIState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> Unit
        }
    }
}
