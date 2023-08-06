package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid

public data class User(
    val id: Uuid,
    val email: String,
    val tag: String,
)
