package williankl.bpProject.common.data.networking

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

public suspend inline fun <reified T> HttpResponse.handleResponse(): T? {
    return when (status) {
        HttpStatusCode.NotFound,
        HttpStatusCode.NoContent -> null

        else -> body()
    }
}

public suspend inline fun <reified T> HttpResponse.handleListResponse(): List<T> {
    return when (status) {
        HttpStatusCode.NotFound,
        HttpStatusCode.NoContent -> emptyList()

        else -> body()
    }
}
