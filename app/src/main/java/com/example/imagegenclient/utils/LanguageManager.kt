package com.example.imagegenclient.utils

import android.content.Context
import android.content.SharedPreferences

object LanguageManager {
    private const val PREFS_NAME = "settings"
    private const val LANGUAGE_KEY = "language"

    fun saveLanguage(context: Context, languageCode: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, languageCode).apply()
    }

    fun getSavedLanguage(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(LANGUAGE_KEY, null)
    }
}
