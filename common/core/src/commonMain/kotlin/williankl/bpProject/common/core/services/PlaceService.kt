package williankl.bpProject.common.core.services

import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.User

public interface PlaceService {
    public suspend fun createPlace(
        user: User,
        place: Place,
    )
    public suspend fun retrievePlace(id: String): Place?
}