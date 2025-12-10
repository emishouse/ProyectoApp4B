package com.example.proyectoapp4b.navigation

// ----------------------------------------------------------------------------------------------
// IMPORTS
// ----------------------------------------------------------------------------------------------

import androidx.compose.runtime.Composable
// Permite crear funciones composables (pantallas, layouts, UI) en Compose.

import androidx.lifecycle.viewmodel.compose.viewModel
// Permite obtener un ViewModel dentro de un composable, sin recrearlo cada vez.

import androidx.navigation.NavType
// Define los tipos de argumentos en la navegación (String, Int, Bool, etc.)

import androidx.navigation.compose.NavHost
// Contenedor que administra toda la navegación y las rutas disponibles.

import androidx.navigation.compose.composable
// Permite crear una ruta dentro de NavHost.

import androidx.navigation.compose.rememberNavController
// Crea y recuerda un NavController para manejar la navegación.

import androidx.navigation.navArgument
// Permite declarar argumentos que acepta cada pantalla (ej. username, id).

import com.example.proyectoapp4b.ui.login.LoginScreen
import com.example.proyectoapp4b.ui.menu.MenuScreen
import com.example.proyectoapp4b.ui.historial.HistorialAcademicoScreen
import com.example.proyectoapp4b.ui.historial.DetalleCuatrimestreScreen
import com.example.proyectoapp4b.ui.perfil.PerfilScreen
import com.example.proyectoapp4b.ui.recuperar.RecuperarContrasenaScreen
// Cada uno de estos son tus pantallas (UI) de la app.

import com.example.proyectoapp4b.viewmodel.LoginViewModel
// ViewModel que maneja la lógica del login (datos, validaciones, sesión).

/**
 * AppNavHost
 * -----------------------------------------------------------------------------------------------
 * Este archivo define TODA la navegación de la app.
 *
 * Contiene:
 *  - El NavController que mueve al usuario entre pantallas
 *  - Las rutas disponibles (login, menú, historial, perfil, etc.)
 *  - Los argumentos que recibe cada pantalla
 *  - Las acciones de navegación (login, logout, módulos)
 *
 * Es el MAPA CENTRAL de la app.
 */

@Composable
fun AppNavHost() {

    // ------------------------------------------------------------------------------------------
    // NAV CONTROLLER
    // Es el "cerebro" de la navegación: permite moverse entre pantallas
    // ------------------------------------------------------------------------------------------
    val navController = rememberNavController()

    // ------------------------------------------------------------------------------------------
    // VIEWMODEL DEL LOGIN
    // Se mantiene vivo mientras la app está abierta
    // No se recrea en cada pantalla
    // ------------------------------------------------------------------------------------------
    val loginViewModel: LoginViewModel = viewModel()

    // ------------------------------------------------------------------------------------------
    // NAVHOST
    // startDestination = "login" → Pantalla principal
    // Aquí definimos TODAS las rutas
    // ------------------------------------------------------------------------------------------
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ======================================================================================
        // LOGIN SCREEN
        // ======================================================================================
        composable("login") {

            LoginScreen(
                viewModel = loginViewModel,

                // ----------------------------------------------------------
                // LOGIN EXITOSO → IR AL MENU
                //
                // popUpTo("login") { inclusive = true }:
                //   - Borra el login del historial
                //   - Impide regresar con botón atrás
                // ----------------------------------------------------------
                onLoginSuccess = { username ->
                    navController.navigate("menu/$username") {
                        popUpTo("login") { inclusive = true }
                    }
                },

                // Ir a la pantalla de recuperar contraseña
                onForgotPassword = {
                    navController.navigate("recuperar")
                }
            )
        }

        // ======================================================================================
        // RECUPERAR CONTRASEÑA
        // ======================================================================================
        composable("recuperar") {
            RecuperarContrasenaScreen(
                onBack = { navController.popBackStack() } // ← Regresa una pantalla atrás
            )
        }

        // ======================================================================================
        // MENU PRINCIPAL
        // ======================================================================================
        composable(
            "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->

            val username = entry.arguments?.getString("username") ?: ""

            MenuScreen(
                username = username,

                // ----------------------------------------------------------
                // LOGOUT → REGRESAR AL LOGIN
                //
                // popUpTo("menu/{username}") no sirve, porque username cambia.
                //
                // Usamos popUpTo("menu") para borrar todo lo anterior y que
                // NO SE PUEDA REGRESAR al menú con botón atrás.
                // ----------------------------------------------------------
                onLogout = {
                    loginViewModel.resetLogin()

                    navController.navigate("login") {
                        // Limpia TODAS las pantallas relacionadas al menú
                        popUpTo("menu/{username}") { inclusive = true }
                    }
                },

                // Opciones del menú (Historial / Perfil)
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/$username")
                        "perfil" -> navController.navigate("perfil/$username")
                    }
                },

                // Desde otras pantallas volver al menú principal
                onMenuPrincipal = {
                    navController.navigate("menu/$username") {
                        popUpTo("menu/$username") { inclusive = true }
                    }
                }
            )
        }

        // ======================================================================================
        // HISTORIAL ACADÉMICO
        // ======================================================================================
        composable(
            "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->

            val username = entry.arguments?.getString("username") ?: ""

            HistorialAcademicoScreen(
                navController = navController,
                username = username,

                // Logout igual que antes
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

        // ======================================================================================
        // DETALLE DE CUATRIMESTRE
        // ======================================================================================
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

        // ======================================================================================
        // PERFIL DEL USUARIO
        // ======================================================================================
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


/* ================================================================================================
   ¿CÓMO SE RELACIONA ESTE ARCHIVO CON EL RESTO DEL PROYECTO?
   ================================================================================================
   ✔ ES EL CORAZÓN DE LA NAVEGACIÓN
     - Define TODAS las rutas que usa la app: login, menú, historial, perfil, detalle, recuperar.
     - Controla la navegación entre pantallas usando NavController.

   ✔ SE CONECTA CON LOS VIEWMODELS
     - Especialmente con LoginViewModel, que persiste mientras el usuario navega.
     - Mantiene la sesión del usuario y controla logout/reset de datos.

   ✔ MANEJA LOS ARGUMENTOS ENTRE PANTALLAS
     - username → se usa para cargar información del usuario en el historial y perfil.
     - numero → se usa para consultar el cuatrimestre seleccionado.

   ✔ ES EL PUNTO CENTRAL ENTRE UI Y LÓGICA
     - Cada pantalla obtiene datos desde ViewModels.
     - Los ViewModels obtienen datos desde los Repositories.
     - Los Repositories consumen el ApiService.
     - AppNavHost es el que enlaza esas pantallas y sus flujos.

   En resumen:
   **AppNavHost es la columna vertebral de la estructura de navegación del proyecto SIGO.
   Comunica pantallas, envía parámetros, maneja sesiones y define cómo el usuario recorre
   toda la aplicación.**
   ================================================================================================ */







