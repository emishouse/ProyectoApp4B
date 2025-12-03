package com.example.proyectoapp4b.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapp4b.ui.LoginScreen
import com.example.proyectoapp4b.ui.menu.MenuScreen
import com.example.proyectoapp4b.ui.historial.HistorialAcademicoScreen

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
                onLoginSuccess = { username ->
                    navController.navigate("menu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // MENÚ CON NOMBRE DEL USUARIO
        composable(
            route = "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"

            MenuScreen(
                username = username,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/$username")
                        "perfil" -> { /* pendiente */ }
                        "kardex" -> { /* pendiente */ }
                        "horario" -> { /* pendiente */ }
                    }
                },
                onMenuPrincipal = {
                    // Ya estás en el menú principal
                }
            )
        }

        // HISTORIAL ACADÉMICO CON NOMBRE
        composable(
            route = "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"

            HistorialAcademicoScreen(
                navController = navController,
                username = username,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$username") {
                        popUpTo("menu/$username") { inclusive = true }
                    }
                }
            )
        }
    }
}


