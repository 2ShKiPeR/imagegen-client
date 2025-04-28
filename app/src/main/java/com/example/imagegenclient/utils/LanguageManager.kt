package com.example.imagegenclient.utils

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "settings"
private const val LANGUAGE_KEY = "language"

class LanguageManager {

    fun saveLanguage(context: Context, languageCode: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, languageCode).apply()
    }

    fun getSavedLanguage(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(LANGUAGE_KEY, "ru") // по умолчанию русский
    }

    fun toggleLanguage(context: Context) {
        val currentLang = getSavedLanguage(context)
        val newLang = if (currentLang == "ru") "en" else "ru"
        saveLanguage(context, newLang)
    }
}
