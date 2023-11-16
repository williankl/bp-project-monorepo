package williankl.bpProject.common.data.networking.core

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import williankl.bpProject.common.data.networking.core.models.PagingResult
import williankl.bpProject.common.data.networking.core.models.PagingState

public suspend inline fun <reified T> HttpResponse.handleResponse(): T? {
    return when (status) {
        HttpStatusCode.NotFound,
        HttpStatusCode.NoContent -> null

        else -> body()
    }
}

public suspend inline fun <reified T> HttpResponse.handleListResponse(): List<T> {
    return when (status) {
        HttpStatusCode.NotFound,
        HttpStatusCode.NoContent -> emptyList()

        else -> body()
    }
}

public fun <T> MutableStateFlow<PagingState<T>>.setLoading(isLoading: Boolean) {
    update { state ->
        state.copy(
            isLoading = isLoading,
        )
    }
}

public fun <T> MutableStateFlow<PagingState<T>>.updateResult(
    onUpdate: PagingResult<T>.() -> PagingResult<T>,
) {
    update { state ->
        state.copy(
            pagingResult = state.pagingResult.onUpdate(),
        )
    }
}

public val <T> StateFlow<PagingState<T>>.result: PagingResult<T>
    get() = value.pagingResult

public val <T> StateFlow<PagingState<T>>.isLoading: Boolean
    get() = value.isLoading

public val <T> StateFlow<PagingState<T>>.currentPage: Int
    get() = value.pagingResult.currentPage

public val <T> StateFlow<PagingState<T>>.hasReachedFinalPage: Boolean
    get() = value.pagingResult.hasReachedFinalPage
