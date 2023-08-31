package williankl.bpProject.common.core.models.network.response

import kotlinx.serialization.Serializable

@Serializable
public data class NetworkErrorResponse(
    val code: String? = null,
    val message: String,
)
