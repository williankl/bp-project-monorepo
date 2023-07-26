package williankl.bpProject.common.data.placeService

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.last
import williankl.bpProject.common.data.placeService.models.PlacesFirebaseModel

internal suspend fun <T> Task<T>.retrieve(): T =
    callbackFlow<T> {
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySendBlocking(task.result)
                close()
            } else {
                close(task.exception)
            }
            close(CancellationException())
        }
        awaitClose()
    }.last()

internal suspend inline fun <reified T> Query.lastOrNull(): T? {
    return get()
        .retrieve()
        .lastOrNull()
        ?.toObject<T>()
}

internal suspend inline fun <reified T> Query.withLastOrNull(
    builder: (QueryDocumentSnapshot?) -> T?
): T? {
    return get()
        .retrieve()
        .lastOrNull()
        ?.let(builder)
}