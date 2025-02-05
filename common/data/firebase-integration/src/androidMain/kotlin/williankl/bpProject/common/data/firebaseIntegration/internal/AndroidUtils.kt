package williankl.bpProject.common.data.firebaseIntegration.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.last
import kotlin.coroutines.cancellation.CancellationException

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
