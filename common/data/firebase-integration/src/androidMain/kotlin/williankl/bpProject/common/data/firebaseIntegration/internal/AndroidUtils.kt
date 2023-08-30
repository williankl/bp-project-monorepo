package williankl.bpProject.common.data.placeService.internal

import com.google.android.gms.tasks.Task
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.last

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