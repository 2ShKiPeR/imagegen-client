package com.example.imagegenclient.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imagegenclient.R
import com.example.imagegenclient.network.ImageRetrofitClient
import com.example.imagegenclient.utils.decodeBase64ToBitmap
import kotlinx.coroutines.launch

@Composable
fun GenerateScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var generatedBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (generatedBitmap != null) {
            Image(
                bitmap = generatedBitmap!!.asImageBitmap(),
                contentDescription = "Сгенерированное изображение",
                modifier = Modifier
                    .size(250.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .background(Color.LightGray)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    try {
                        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzQ1ODQ4NzEzLCJleHAiOjE3NDU4ODQ3MTN9.laTPoEY6Z6970VDqdnF4HlW7D0fVF_sujHNsGXZNvag"
                        val response = ImageRetrofitClient.apiService.generateImage(token)
                        val bitmap = decodeBase64ToBitmap(response.image)
                        generatedBitmap = bitmap
                    } catch (e: Exception) {
                        Log.e("Error", "Ошибка генерации", e)
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isLoading) stringResource(id = R.string.generation) else stringResource(id = R.string.generate_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate("checkOriginality")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.check_originality_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                // Пока не реализовано
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.buy_subscription_button))
        }
    }
}
