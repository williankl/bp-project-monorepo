package williankl.bpProject.common.data.networking.core.models.response

import kotlinx.serialization.Serializable

@Serializable
public data class NetworkErrorResponse(
    val code: String? = null,
    val message: String,
)
