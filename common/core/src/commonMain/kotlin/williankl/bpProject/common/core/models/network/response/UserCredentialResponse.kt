package williankl.bpProject.common.core.models.network.response

import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.User

@Serializable
public data class UserCredentialResponse(
    val bearerToken: String,
    val user: User,
)
