package williankl.bpProject.common.platform.stateHandler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
public fun <T> RunnerModel<T>.collectData(): State<T> {
    val router = LocalRouter.currentOrThrow
    val modelUIState by currentUIState.collectAsState()

    LaunchedEffect(modelUIState.modelState) {
        router.updateUIState(modelUIState.modelState)
    }

    return remember {
        derivedStateOf { modelUIState.content }
    }
}
