package com.example.imagegenclient.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imagegenclient.R
import com.example.imagegenclient.model.AuthRequest
import com.example.imagegenclient.model.AuthResponse
import com.example.imagegenclient.network.RetrofitClient
import com.example.imagegenclient.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(id = R.string.username_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.password_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )


            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        val authRequest = AuthRequest(username, password)

                        RetrofitClient.apiService.login(authRequest)
                            .enqueue(object : Callback<AuthResponse> {
                                override fun onResponse(
                                    call: Call<AuthResponse>,
                                    response: Response<AuthResponse>
                                ) {
                                    if (response.isSuccessful && response.body() != null) {
                                        val token = response.body()!!.token
                                        Toast.makeText(context, "Успешный вход!", Toast.LENGTH_SHORT).show()

                                        // Переход на следующий экран после успешного логина
                                        navController.navigate(Screen.Generate.route)
                                    } else {
                                        Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                                    Toast.makeText(context, "Ошибка сети: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            })
                    } else {
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.login_button))
            }
        }
    }
}
