package williankl.bpProject.common.platform.stateHandler

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import williankl.bpProject.common.platform.stateHandler.models.ModelState
import williankl.bpProject.common.platform.stateHandler.models.UIState
import kotlin.time.Duration.Companion.seconds

public abstract class RunnerModel<T>(
    initialData: T,
    private val dispatcher: CoroutineDispatcher,
) : ScreenModel {

    private val mutableUIState: MutableStateFlow<UIState<T>> = MutableStateFlow(
        UIState(
            modelState = ModelState.Content,
            content = initialData,
        )
    )

    public val currentUIState: StateFlow<UIState<T>> = mutableUIState

    public val currentData: T
        get() = currentUIState.value.content

    public fun setContent(
        onContent: (T) -> Unit = { result ->
            mutableUIState.update {
                UIState(
                    modelState = ModelState.Content,
                    content = result
                )
            }
        },
        onError: (Throwable) -> Unit = { reason ->
            mutableUIState.update { currentData ->
                UIState(
                    modelState = ModelState.Error(reason),
                    content = currentData.content
                )
            }
        },
        onLoading: () -> Unit = {
            mutableUIState.update { currentData ->
                UIState(
                    modelState = ModelState.Loading,
                    content = currentData.content
                )
            }
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
            mutableUIState.update { currentData ->
                UIState(
                    modelState = ModelState.Content,
                    content = currentData.content
                )
            }
        },
        onError: (Throwable) -> Unit = { reason ->
            mutableUIState.update { currentData ->
                UIState(
                    modelState = ModelState.Error(reason),
                    content = currentData.content
                )
            }
        },
        onLoading: () -> Unit = {
            mutableUIState.update { currentData ->
                UIState(
                    modelState = ModelState.Loading,
                    content = currentData.content
                )
            }
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
        mutableUIState.update { currentData ->
            UIState(
                modelState = ModelState.Content,
                content = action(currentData.content)
            )
        }
    }

    private suspend fun <R> runCatchingOnModel(
        onContent: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onLoading: () -> Unit,
        action: suspend () -> R,
    ) {
        return runCatching {
            onLoading()
            delay(5.seconds)
            action()
        }.fold(
            onSuccess = { result ->
                onContent(result)
            },
            onFailure = { error ->
                error.printStackTrace()
                onError(error)
            },
        )
    }
}
