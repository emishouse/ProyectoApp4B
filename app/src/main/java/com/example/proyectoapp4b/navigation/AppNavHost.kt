package com.example.proyectoapp4b.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapp4b.ui.LoginScreen
import com.example.proyectoapp4b.ui.menu.MenuScreen
import com.example.proyectoapp4b.ui.historial.HistorialAcademicoScreen
import com.example.proyectoapp4b.viewmodel.LoginViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    // ViewModel compartido
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { username ->
                    navController.navigate("menu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // MENÚ
        composable(
            "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: "Usuario"

            MenuScreen(
                username = username,
                onLogout = {
                    loginViewModel.resetLogin()   // <-- 🔥 IMPORTANTE
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/$username")
                        "perfil" -> {}
                        "kardex" -> {}
                        "horario" -> {}
                    }
                },
                onMenuPrincipal = {}
            )
        }

        // HISTORIAL ACADÉMICO
        composable(
            "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: "Usuario"

            HistorialAcademicoScreen(
                navController = navController,
                username = username,
                onLogout = {
                    loginViewModel.resetLogin()
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



