package williankl.bpProject.common.platform.uriNavigator

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import com.chrynan.uri.core.Uri
import android.net.Uri as AndroidUri

public actual class UriNavigator(
    private val context: Context,
) {
    public actual fun redirectFromString(uriString: String) {
        val uri = AndroidUri.parse(uriString)
        val intent = Intent(ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    public actual fun redirectFromUri(uri: Uri) {
        val intent = Intent(ACTION_VIEW, uri.toAndroidUri())
        context.startActivity(intent)
    }

    private fun Uri.toAndroidUri() = AndroidUri.parse(uriString)
}
