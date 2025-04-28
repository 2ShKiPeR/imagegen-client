package com.example.imagegenclient.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.imagegenclient.ui.CheckOriginalityScreen
import com.example.imagegenclient.ui.LoginScreen
import com.example.imagegenclient.ui.StartScreen
import com.example.imagegenclient.ui.GenerateScreen
import com.example.imagegenclient.ui.RegisterScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Register : Screen("register")
    object Login : Screen("login")
    object Generate : Screen("generate")
    object CheckOriginality : Screen("checkOriginality")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(Screen.Start.route) {
            StartScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Generate.route) {
            GenerateScreen(navController)
        }
        composable(Screen.CheckOriginality.route) {
            CheckOriginalityScreen()
        }
    }
}
