package williankl.bpProject.server.database.internal.user

import user.UserData
import williankl.bpProject.common.core.models.User

internal object Mapper {
    fun toDomain(from: UserData): User {
        return with(from) {
            User(
                id = id,
                tag = tag,
                email = email,
            )
        }
    }

    fun fromDomain(from: User): UserData {
        return with(from) {
            UserData(
                id = id,
                email = email,
                tag = tag,
            )
        }
    }
}