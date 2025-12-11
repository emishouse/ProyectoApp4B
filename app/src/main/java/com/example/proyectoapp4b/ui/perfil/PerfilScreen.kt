package com.example.proyectoapp4b.ui.perfil
// Paquete donde vive esta pantalla. Organiza lógicamente todas las pantallas relacionadas con el perfil del usuario.

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
// Importa los elementos de layout básicos (Column, Row, Spacer, etc.)

import androidx.compose.foundation.rememberScrollState
// Permite recordar el estado del scroll para que la pantalla pueda desplazarse verticalmente.

import androidx.compose.foundation.verticalScroll
// Habilita el comportamiento de scroll vertical dentro de un layout.

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
// Importa el icono de flecha hacia atrás para el botón de navegación.

import androidx.compose.material3.*
// Importa todos los componentes Material 3 usados: Text, Card, IconButton, etc.

import androidx.compose.runtime.*
// Importa las funciones y propiedades reactivas: remember, mutableStateOf, collectAsState, etc.

import androidx.compose.ui.Alignment
// Controla alineaciones dentro de filas y columnas.

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// Permite aplicar configuraciones visuales y de interacción a los composables (padding, fillMaxSize, etc).

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
// Permite definir pesos/tipos de estilo tipográfico (Bold, Medium, etc.)

import androidx.compose.ui.tooling.preview.Preview
// Habilita la anotación @Preview para ver esta pantalla en Android Studio.

import androidx.compose.ui.unit.dp
// Define medidas con la unidad dp.

import androidx.lifecycle.viewmodel.compose.viewModel
// Permite obtener una instancia del ViewModel asociada a esta pantalla.

import androidx.navigation.NavController
// Controlador de navegación para cambiar entre pantallas.

import androidx.navigation.compose.rememberNavController
// Permite crear un NavController para el @Preview.

import com.example.proyectoapp4b.ui.components.SigoTopBar
// Importa la barra superior reutilizable que muestra usuario, logout y botón de menú principal.

// Reutilicé la data class Usuario y PerfilViewModel que ya tenías.
// Asegúrate de que PerfilViewModel esté en el package com.example.proyectoapp4b.ui.perfil


@Composable
fun PerfilScreen(
    navController: NavController,
    username: String,
    personFullName: String,
    email: String,
    profileName: String,
    accessModule: String,
    personId: Int,
    id: Int,
    register: String,
    roles: List<String>,
    active: Boolean,
    termsConditions: Boolean,
    onLogout: () -> Unit,
    onMenuPrincipal: () -> Unit,
    viewModel: PerfilViewModel = viewModel()
) {
    val usuarioState by viewModel.usuario.collectAsState()

    LaunchedEffect(username) {
        viewModel.cargarPerfil(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // ✅ TopBar institucional con logout y navegación
        SigoTopBar(
            username = username,
            onLogout = {
                onLogout()
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            onMenuPrincipal = {
                navController.navigate("menu/$username") {
                    popUpTo("menu/$username") { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔙 Flecha + título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Mi Perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Datos básicos (Nombre, Usuario, Email)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(0.dp), // 🔹 sin sombra
            colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 🔹 sin fondo
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: $personFullName", style = MaterialTheme.typography.bodyLarge)
                Text("Usuario: $username", style = MaterialTheme.typography.bodyLarge)
                Text("Email: $email", style = MaterialTheme.typography.bodyLarge)
                Text("Perfil: $profileName", style = MaterialTheme.typography.bodyLarge)
                Text("Módulo: $accessModule", style = MaterialTheme.typography.bodyLarge)
                Text("Roles: ${roles.joinToString(", ")}", style = MaterialTheme.typography.bodyLarge)
                Text("ID Persona: $personId", style = MaterialTheme.typography.bodyLarge)
                Text("ID Registro: $id", style = MaterialTheme.typography.bodyLarge)
                Text("Usuario activo: ${if (active) "Sí" else "No"}", style = MaterialTheme.typography.bodyLarge)
                Text("Fecha de registro: $register", style = MaterialTheme.typography.bodyLarge)
                Text("Términos y condiciones: ${if (termsConditions) "Aceptados" else "No aceptados"}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
/**
 * RELACIÓN DE ESTA PANTALLA CON EL RESTO DEL PROYECTO
 *
 * - PerfilScreen depende de:
 *   ✔ PerfilViewModel → maneja los datos del usuario, carga la información y actualiza los campos.
 *   ✔ Usuario (data class) → estructura del perfil del usuario.
 *   ✔ SigoTopBar → barra superior que muestra usuario, menú principal y logout.
 *   ✔ NavController → permite navegar hacia:
 *        • login  → cuando se hace logout
 *        • menu/{username} → para volver al menú principal
 *
 * - Funciona así:
 *   1. Recibe el username desde el menú o desde login.
 *   2. Usa LaunchedEffect para cargar los datos del usuario desde el ViewModel.
 *   3. PerfilViewModel expone un Flow llamado usuario.
 *   4. collectAsState convierte ese Flow a un estado observable por Compose.
 *   5. Los TextFields llaman funciones del ViewModel que actualizan los valores.
 *   6. Las tarjetas (PerfilCard) solo agrupan visualmente los datos.
 *
 * - Pantallas relacionadas:
 *   ✔ LoginScreen → proporciona el username y redirige a MenuScreen.
 *   ✔ MenuScreen → permite navegar hacia "perfil" usando navigate("perfil").
 *   ✔ PerfilScreen ← esta pantalla.
 */



