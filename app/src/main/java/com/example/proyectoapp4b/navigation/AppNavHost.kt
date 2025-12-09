package com.example.proyectoapp4b.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapp4b.ui.login.LoginScreen
import com.example.proyectoapp4b.ui.menu.MenuScreen
import com.example.proyectoapp4b.ui.historial.HistorialAcademicoScreen
import com.example.proyectoapp4b.ui.historial.DetalleCuatrimestreScreen
import com.example.proyectoapp4b.ui.perfil.PerfilScreen
import com.example.proyectoapp4b.ui.recuperar.RecuperarContrasenaScreen
import com.example.proyectoapp4b.viewmodel.LoginViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
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
                },
                onForgotPassword = {
                    navController.navigate("recuperar")
                }
            )
        }

        // RECUPERAR CONTRASEÑA
        composable("recuperar") {
            RecuperarContrasenaScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // MENÚ
        composable(
            "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: ""

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
                        "perfil" -> navController.navigate("perfil/$username")
                    }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$username") {
                        popUpTo("menu/$username") { inclusive = true }
                    }
                }
            )

        }

        // HISTORIAL ACADÉMICO
        composable(
            "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: ""

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

        // DETALLE DE CUATRIMESTRE
        composable(
            route = "detalleCuatrimestre/{numero}/{username}",
            arguments = listOf(
                navArgument("numero") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { entry ->
            val numero = entry.arguments?.getInt("numero") ?: 0
            val username = entry.arguments?.getString("username") ?: ""

            DetalleCuatrimestreScreen(
                navController = navController,
                numero = numero,
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
            val username = entry.arguments?.getString("username") ?: ""

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
    }
}






