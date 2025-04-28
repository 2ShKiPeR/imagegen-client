package com.example.imagegenclient

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.imagegenclient.navigation.NavGraph
import com.example.imagegenclient.utils.LanguageManager
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем язык из сохранённых настроек
        val LanguageManager = LanguageManager()
        LanguageManager.getSavedLanguage(this)?.let { lang ->
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
