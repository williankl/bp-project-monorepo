package williankl.bpProject.common.data.preferencesHandler

import android.content.Context
import android.content.SharedPreferences
import williankl.bpProject.common.data.preferencesHandler.models.PreferredTheme

internal class PreferencesHandlerInfrastructure(
    private val context: Context,
) : PreferencesHandler {

    private companion object {
        const val SHARED_KEYS = "shared-prefs-key"
        const val BEARER_TOKEN_KEY = "bearer-token-key"
        const val PREFERRED_THEME_KEY = "preferred-theme-key"
    }

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(SHARED_KEYS, Context.MODE_PRIVATE)

    override fun setPreferredTheme(theme: PreferredTheme) {
        onSharedPreferences {
            putString(PREFERRED_THEME_KEY, theme.name)
        }
    }

    override fun userPreferredTheme(): PreferredTheme {
        return sharedPreferences.getString(PREFERRED_THEME_KEY, null)
            .toPreferredTheme()
    }

    override fun setBearerToken(token: String) {
        onSharedPreferences {
            putString(BEARER_TOKEN_KEY, token)
        }
    }

    override fun userBearerToken(): String? {
        return sharedPreferences.getString(BEARER_TOKEN_KEY, null)
    }

    private fun onSharedPreferences(
        action: SharedPreferences.Editor.() -> Unit
    ) {
        val editingScope = sharedPreferences.edit()
        with(editingScope) {
            action()
        }
        editingScope.apply()
    }

    private fun String?.toPreferredTheme(): PreferredTheme {
        return PreferredTheme.entries
            .firstOrNull { theme -> theme.name == this }
            ?: PreferredTheme.System
    }
}
