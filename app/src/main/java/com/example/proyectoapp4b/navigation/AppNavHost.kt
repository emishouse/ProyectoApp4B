package com.example.proyectoapp4b.navigation

/* ================================================================================================
   IMPORTS
   ================================================================================================
   - androidx.compose.runtime.Composable
       Permite crear funciones composables que construyen interfaces en Jetpack Compose.

   - viewModel()
       Obtiene o crea una instancia del ViewModel asociado al ciclo de vida del NavHost.

   - NavHost, composable, navArgument, NavType
       Componentes del sistema de navegación de Jetpack Compose. Permiten definir rutas,
       argumentos y pantallas dentro del árbol de navegación.

   - rememberNavController()
       Crea y recuerda un NavController, que permite movernos entre pantallas.

   - Pantallas de la app (LoginScreen, MenuScreen, etc.)
       Se importan porque cada ruta del NavHost debe renderizar una pantalla específica.

   - LoginViewModel
       ViewModel encargado del manejo del estado y lógica del módulo de Login.
   ================================================================================================ */

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

/**
 * AppNavHost
 * -----------------------------------------------------------------------------------------------
 * Define toda la estructura de navegación de la aplicación.
 *
 * Contiene:
 *  - Controlador de navegación (NavController)
 *  - Todas las rutas disponibles
 *  - Los argumentos que recibe cada pantalla
 *  - Acciones de navegación (login, menú, historial, perfil, recuperar contraseña)
 *
 * Es el "mapa" central de toda la app.
 */
@Composable
fun AppNavHost() {

    // Controlador que permite navegar entre pantallas.
    val navController = rememberNavController()

    // ViewModel que se mantiene mientras la app esté abierta.
    val loginViewModel: LoginViewModel = viewModel()

    /* --------------------------------------------------------------------------------------------
       NavHost
       - Indica cuál será la pantalla inicial (login)
       - Define las rutas de la aplicación
       -------------------------------------------------------------------------------------------- */
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        /* ========================================================================================
           LOGIN
           ======================================================================================== */
        composable("login") {

            // Renderiza la pantalla de login y define las acciones del usuario
            LoginScreen(
                viewModel = loginViewModel,

                // Cuando el login es exitoso, navega al menú enviando el username
                onLoginSuccess = { username ->
                    navController.navigate("menu/$username") {
                        popUpTo("login") { inclusive = true } // Evita regresar al login
                    }
                },

                // Navegación a la pantalla de recuperar contraseña
                onForgotPassword = {
                    navController.navigate("recuperar")
                }
            )
        }


        /* ========================================================================================
           RECUPERAR CONTRASEÑA
           ======================================================================================== */
        composable("recuperar") {
            RecuperarContrasenaScreen(
                onBack = { navController.popBackStack() } // Regresa a la pantalla anterior
            )
        }


        /* ========================================================================================
           MENÚ PRINCIPAL
           ======================================================================================== */
        composable(
            "menu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { entry ->

            val username = entry.arguments?.getString("username") ?: ""

            MenuScreen(
                username = username,

                // Cierra sesión y regresa al login
                onLogout = {
                    loginViewModel.resetLogin()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },

                // Redirige a módulos del menú (historial, perfil, etc.)
                onNavigateModules = { module ->
                    when (module) {
                        "historial" -> navController.navigate("historial/$username")
                        "perfil" -> navController.navigate("perfil/$username")
                    }
                },

                // Regresar al menú principal desde otras pantallas
                onMenuPrincipal = {
                    navController.navigate("menu/$username") {
                        popUpTo("menu/$username") { inclusive = true }
                    }
                }
            )
        }


        /* ========================================================================================
           HISTORIAL ACADÉMICO
           ======================================================================================== */
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


        /* ========================================================================================
           DETALLE DE CUATRIMESTRE
           ======================================================================================== */
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


        /* ========================================================================================
           PERFIL DEL USUARIO
           ======================================================================================== */
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







