package williankl.bpProject.common.platform.stateHandler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public abstract class RunnerModel<T>(
    initialData: T,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    private val mutableUiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Content)
    public val uiState: StateFlow<UIState> = mutableUiState

    private val mutableCurrentData: MutableStateFlow<T> = MutableStateFlow(initialData)
    public val currentData: StateFlow<T> = mutableCurrentData

    public fun setContent(
        onContent: (T) -> Unit = { result ->
            mutableCurrentData.update { result }
            mutableUiState.update { UIState.Content }
        },
        onError: (Throwable) -> Unit = { reason ->
            mutableUiState.update { UIState.Error(reason) }
        },
        onLoading: () -> Unit = {
            mutableUiState.update { UIState.Loading }
        },
        action: suspend () -> T,
    ) {
        coroutineScope.launch(dispatcher) {
            runCatchingOnModel(
                onContent = onContent,
                onError = onError,
                onLoading = onLoading,
                action = action,
            )
        }
    }

    public fun runAsync(
        onContent: () -> Unit = {
            mutableUiState.update { UIState.Content }
        },
        onError: (Throwable) -> Unit = { reason ->
            mutableUiState.update { UIState.Error(reason) }
        },
        onLoading: () -> Unit = {
            mutableUiState.update { UIState.Loading }
        },
        action: suspend () -> Unit,
    ) {
        coroutineScope.launch(dispatcher) {
            runCatchingOnModel(
                onContent = { onContent() },
                onError = onError,
                onLoading = onLoading,
                action = action,
            )
        }
    }

    public fun updateData(
        action: (T) -> T,
    ) {
        mutableCurrentData.update { action(currentData.value) }
    }

    private suspend fun <R> runCatchingOnModel(
        onContent: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onLoading: () -> Unit,
        action: suspend () -> R,
    ) {
        return runCatching {
            onLoading()
            action()
        }.fold(
            onSuccess = { result ->
                onContent(result)
            },
            onFailure = { error ->
                onError(error)
            },
        )
    }
}
