package com.example.imagegenclient.ui

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imagegenclient.R
import com.example.imagegenclient.navigation.Screen
import com.example.imagegenclient.utils.LanguageManager

@Composable
fun StartScreen(navController: NavController) {
    val context = LocalContext.current
    val languageManager = LanguageManager()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Кнопка смены языка в правом верхнем углу
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = if (languageManager.getSavedLanguage(context) == "ru") "RU" else "UK",
                modifier = Modifier
                    .clickable {
                        languageManager.toggleLanguage(context)
                        (context as? Activity)?.recreate()
                    }
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Основные кнопки
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.login_button))
            }

            Button(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.register_button))
            }
        }
    }
}
