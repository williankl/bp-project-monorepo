package williankl.bpProject.common.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
public data class LoginData(
    val userName: String? = null,
    val email: String? = null,
    val password: String,
)