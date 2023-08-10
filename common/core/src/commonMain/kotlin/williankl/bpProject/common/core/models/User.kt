package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class User(
    @Serializable(UuidSerializer::class) val id: Uuid,
    val email: String,
    val tag: String,
)
