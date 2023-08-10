package williankl.bpProject.common.platform.stateHandler.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

public abstract class StateModel<T>(
    initialData: T,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    public var currentData: T by mutableStateOf(initialData)
        private set

    protected fun setContent(
        onContent: (T) -> Unit = { result -> currentData = result },
        onError: (Throwable) -> Unit = { /* Nothing by default */ },
        onLoading: () -> Unit = { /* Nothing by default */ },
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

    protected fun runAsync(
        onContent: () -> Unit = { /* Nothing by default */ },
        onError: (Throwable) -> Unit = { /* Nothing by default */ },
        onLoading: () -> Unit = { /* Nothing by default */ },
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

    protected fun updateData(
        action: (T) -> T,
    ) {
        currentData = action(currentData)
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
