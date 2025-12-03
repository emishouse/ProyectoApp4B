package com.example.proyectoapp4b.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapp4b.ui.LoginScreen
import com.example.proyectoapp4b.ui.menu.MenuScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(
                onLoginSuccess = { username ->   // ← RECIBE EL NOMBRE DEL LOGIN
                    navController.navigate("menu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // MENÚ CON NOMBRE DEL USUARIO
        composable(
            route = "menu/{username}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"

            MenuScreen(
                username = username,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("menu/{username}") { inclusive = true }
                    }
                },
                onNavigateModules = {
                    // A futuro navegarás a módulos:
                    // navController.navigate("kardex")
                }
            )
        }
    }
}

