package com.example.proyectoapp4b.navigation

// ----------------------------------------------------------------------------------------------
// IMPORTS
// ----------------------------------------------------------------------------------------------

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
import com.example.proyectoapp4b.data.AuthRepository
import com.example.proyectoapp4b.data.local.TokenManager
import com.example.proyectoapp4b.di.LoginViewModelFactory
import com.example.proyectoapp4b.findActivity
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
    val context = LocalContext.current

    // Creamos el repositorio
    val repository = AuthRepository(TokenManager(context))

    // Creamos ViewModel usando Factory
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository)
    )

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            val activity = context.findActivity() // ✅ obtiene la Activity real

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { user ->
                    navController.navigate(route = "menu/${user.username}") {
                        popUpTo(route = "login") { inclusive = true }
                    }
                },
                onForgotPassword = { navController.navigate(route = "recuperar") },
                onCloseApp = { activity?.finishAffinity() } // ✅ cierra la app completa
            )
        }

        composable("recuperar") {
            val uiState by loginViewModel.uiState.collectAsState()
            val user = uiState.user

            RecuperarContrasenaScreen(
                onBack = { navController.popBackStack() })
        }

        composable(
            "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->

            val uiState by loginViewModel.uiState.collectAsState()
            val user = uiState.user

            MenuScreen(
                username = user?.username ?: "",
                personFullName = user?.personFullName ?: "",
                email = user?.email ?: "",
                profileName = user?.profileName ?: "",
                accessModule = user?.accessModule?: "",
                personId = user?.personId ?: 0,
                id = user?.id ?: 0,
                register = user?.register ?: "",
                roles = user?.roles ?: listOf(),
                active = user?.active?: false,
                termsConditions = user?.termsConditions?: false,
                onLogout = {
                    loginViewModel.clearFields()
                    navController.navigate("login") {
                        popUpTo("menu/{username}") { inclusive = true }
                    }
                },
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/${user?.username}")
                        "perfil" -> navController.navigate("perfil/${user?.username}")
                    }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/${user?.username}") {
                        popUpTo("menu/${user?.username}") { inclusive = true }
                    }
                }
            )

        }


        composable(
            "historial/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: ""
            HistorialAcademicoScreen(
                navController = navController,
                username = username,
                onLogout = {
                    loginViewModel.clearFields()
                    navController.navigate("login") { popUpTo("login") { inclusive = true } }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$username") { popUpTo("menu/$username") { inclusive = true } }
                }
            )
        }

        composable(
            "detalleCuatrimestre/{numero}/{username}",
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
                    loginViewModel.clearFields()
                    navController.navigate("login") { popUpTo("login") { inclusive = true } }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$username") { popUpTo("menu/$username") { inclusive = true } }
                }
            )
        }

        composable(
            "perfil/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->
            val username = entry.arguments?.getString("username") ?: ""
            val uiState by loginViewModel.uiState.collectAsState()
            val user = uiState.user
            PerfilScreen(
                navController = navController,
                username = user?.username ?: "",
                personFullName = user?.personFullName ?: "",
                email = user?.email ?: "",
                profileName = user?.profileName ?: "",
                accessModule = user?.accessModule?: "",
                personId = user?.personId ?: 0,
                id = user?.id ?: 0,
                register = user?.register ?: "",
                roles = user?.roles ?: listOf(),
                active = user?.active?: false,
                termsConditions = user?.termsConditions?: false,
                onLogout = {
                    loginViewModel.clearFields()
                    navController.navigate("login") { popUpTo("login") { inclusive = true } }
                },
                onMenuPrincipal = {
                    navController.navigate("menu/$username") { popUpTo("menu/$username") { inclusive = true } }
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







