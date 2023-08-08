package williankl.bpProject.server.database.internal.user

import java.util.*
import user.UserCredentials
import user.UserData
import williankl.bpProject.common.core.models.User

internal object Mapper {
    fun toDomain(from: UserData): User {
        return with(from) {
            User(
                id = id.toString(),
                tag = tag,
                email = email,
            )
        }
    }

    fun fromDomain(from: User): UserData {
        return with(from) {
            UserData(
                id = UUID.fromString(id),
                email = email,
                tag = tag,
            )
        }
    }
}