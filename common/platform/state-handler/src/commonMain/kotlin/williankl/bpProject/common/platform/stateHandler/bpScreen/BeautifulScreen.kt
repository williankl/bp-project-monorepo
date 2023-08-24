package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import williankl.bpProject.common.platform.stateHandler.UIState

public abstract class BeautifulScreen : Screen {

    @Composable
    override fun Content() {
        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    protected inline fun <reified T : RunnerModel<*>> rememberRunnerModel(): T {
        val model = rememberScreenModel<T>()
        val modelUiState by model.uiState.collectAsState()
        LaunchedEffect(modelUiState) {
            screenState = modelUiState
        }
        return model
    }
}
