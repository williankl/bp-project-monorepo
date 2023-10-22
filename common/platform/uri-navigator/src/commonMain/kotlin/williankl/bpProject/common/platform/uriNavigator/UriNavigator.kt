package williankl.bpProject.common.platform.uriNavigator

import com.chrynan.uri.core.Uri

public expect class UriNavigator {

    public fun redirectFromString(uriString: String)
    public fun redirectFromUri(uri: Uri)
}