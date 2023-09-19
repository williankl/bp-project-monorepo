package williankl.bpProject.common.data.sessionHandler

import williankl.bpProject.common.data.sessionHandler.models.PreferredTheme

public interface PreferencesHandler {
    public fun setPreferredTheme(theme: PreferredTheme)
    public fun userPreferredTheme(): PreferredTheme
    public fun setBearerToken(token: String)
    public fun userBearerToken(): String?
}
