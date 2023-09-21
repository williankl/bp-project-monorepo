package williankl.bpProject.server.database.internal.user

import user.UserData
import williankl.bpProject.common.core.models.User
import java.util.*

internal object Mapper {
    fun toDomain(from: UserData): User {
        return with(from) {
            User(
                id = id,
                name = name,
                email = email,
                avatarUrl = avatarUrl,
                tag = tag,
            )
        }
    }

    fun fromDomain(from: User): UserData {
        return with(from) {
            UserData(
                id = id,
                name = name,
                email = email,
                avatarUrl = avatarUrl,
                tag = tag,
            )
        }
    }
}
