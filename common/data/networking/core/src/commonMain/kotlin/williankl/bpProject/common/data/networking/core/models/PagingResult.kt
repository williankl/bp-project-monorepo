package williankl.bpProject.common.data.networking.core.models

public data class PagingResult<T>(
    val currentPage: Int = 0,
    val hasReachedFinalPage: Boolean = false,
    val items: List<T> = emptyList(),
)
