package williankl.bpProject.common.core.models

import kotlinx.serialization.Serializable

@Serializable
public data class User(
    val id: String,
    val email: String,
    val tag: String,
)
