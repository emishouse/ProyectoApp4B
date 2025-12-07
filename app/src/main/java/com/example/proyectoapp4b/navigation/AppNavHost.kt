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
import com.example.proyectoapp4b.ui.historial.DetalleCuatrimestreScreen
import com.example.proyectoapp4b.ui.perfil.PerfilScreen   // 👈 importa tu nueva pantalla
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
                    loginViewModel.resetLogin()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/$username")
                        "perfil" -> navController.navigate("perfil/$username")   // 👈 ahora sí navega
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

        // PERFIL
        composable(
            "perfil/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: "Usuario"

            PerfilScreen(
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

        // DETALLE DEL CUATRIMESTRE (dinámico con parámetro)
        composable(
            "detalleCuatrimestre/{numero}",
            arguments = listOf(navArgument("numero") { type = NavType.IntType })
        ) { entry ->
            val numero = entry.arguments?.getInt("numero") ?: 0
            DetalleCuatrimestreScreen(navController = navController, numero = numero)
        }
    }
}


