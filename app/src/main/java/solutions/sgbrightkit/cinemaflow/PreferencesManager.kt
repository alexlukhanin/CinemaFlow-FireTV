package solutions.sgbrightkit.cinemaflow

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "cinemaflow_prefs"
    private const val KEY_DARK_THEME = "dark_theme"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveDarkTheme(context: Context, isDark: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_DARK_THEME, isDark).apply()
    }

    fun isDarkTheme(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_DARK_THEME, false)
    }
}