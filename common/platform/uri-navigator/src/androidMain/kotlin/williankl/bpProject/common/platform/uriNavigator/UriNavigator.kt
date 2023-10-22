package williankl.bpProject.common.platform.uriNavigator

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.chrynan.uri.core.Uri
import android.net.Uri as AndroidUri

public actual class UriNavigator(
    private val context: Context,
) {
    public actual fun redirectFromString(uriString: String) {
        context.startActivity(buildIntent(AndroidUri.parse(uriString)))
    }

    public actual fun redirectFromUri(uri: Uri) {
        context.startActivity(buildIntent(uri.toAndroidUri()))
    }

    private fun buildIntent(uri: AndroidUri): Intent {
        return Intent(ACTION_VIEW, uri)
            .addFlags(FLAG_ACTIVITY_NEW_TASK)
    }

    private fun Uri.toAndroidUri() = AndroidUri.parse(uriString)
}
