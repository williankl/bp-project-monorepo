package williankl.bpProject.common.core.models

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.models.Place.PlaceAddress
import williankl.bpProject.common.core.models.Season
import williankl.bpProject.common.core.serializers.UuidSerializer

@Serializable
public data class PlaceRating(
    @Serializable(UuidSerializer::class) val id: Uuid,
    @Serializable(UuidSerializer::class) val placeId: Uuid,
    val ownerData: User,
    val comment: String?,
    val rating: Int?,
    val createdAt: Long,
    val updatedAt: Long?,
)
