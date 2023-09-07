package williankl.bpProject.common.data.preferencesHandler

import williankl.bpProject.common.data.preferencesHandler.models.PreferredTheme

public interface PreferencesHandler {
    public fun setPreferredTheme(theme: PreferredTheme)
    public fun userPreferredTheme(): PreferredTheme
    public fun setBearerToken(token: String)
    public fun userBearerToken(): String?
}