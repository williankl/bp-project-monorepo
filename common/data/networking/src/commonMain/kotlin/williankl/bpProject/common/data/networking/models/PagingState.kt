package williankl.bpProject.common.data.networking.models

public data class PagingState<T>(
    val pagingResult: PagingResult<T> = PagingResult(),
    val isLoading: Boolean = false,
)
