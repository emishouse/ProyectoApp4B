package com.example.proyectoapp4b.ui.perfil
// Paquete donde vive esta pantalla. Organiza lógicamente todas las pantallas relacionadas con el perfil del usuario.

import androidx.compose.foundation.layout.*
// Importa los elementos de layout básicos (Column, Row, Spacer, etc.)

import androidx.compose.foundation.rememberScrollState
// Permite recordar el estado del scroll para que la pantalla pueda desplazarse verticalmente.

import androidx.compose.foundation.verticalScroll
// Habilita el comportamiento de scroll vertical dentro de un layout.

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
// Importa el icono de flecha hacia atrás para el botón de navegación.

import androidx.compose.material3.*
// Importa todos los componentes Material 3 usados: Text, Card, IconButton, etc.

import androidx.compose.runtime.*
// Importa las funciones y propiedades reactivas: remember, mutableStateOf, collectAsState, etc.

import androidx.compose.ui.Alignment
// Controla alineaciones dentro de filas y columnas.

import androidx.compose.ui.Modifier
// Permite aplicar configuraciones visuales y de interacción a los composables (padding, fillMaxSize, etc).

import androidx.compose.ui.text.font.FontWeight
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
    navController: NavController,        // Controlador de navegación para moverse entre pantallas.
    username: String,                    // Nombre de usuario actual, recibido desde el login o menú.
    onLogout: () -> Unit,                // Acción ejecutada al cerrar sesión.
    onMenuPrincipal: () -> Unit,         // Acción para volver al menú principal.
    viewModel: PerfilViewModel = viewModel()   // ViewModel que maneja los datos del perfil.
) {

    val usuarioState by viewModel.usuario.collectAsState()
    // Se obtiene el estado del usuario desde el ViewModel. collectAsState convierte el Flow en un estado observable por Compose.


    // Cargar datos iniciales al entrar
    LaunchedEffect(username) {
        viewModel.cargarPerfil(username)
        // Ejecuta la carga de datos del perfil cuando el username cambia o la pantalla inicia.
    }

    Column(
        modifier = Modifier
            .fillMaxSize()                   // Ocupa toda la pantalla.
            .verticalScroll(rememberScrollState())  // Permite desplazamiento vertical.
    ) {

        // TopBar: al hacer logout además de ejecutar onLogout limpiamos la navegación
        SigoTopBar(
            username = username,           // Muestra el usuario en la barra superior.
            onLogout = {
                onLogout()                // Ejecuta la acción externa de cierre de sesión.

                // Limpia el historial para evitar volver atrás después del logout.
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            onMenuPrincipal = {
                // Navega al menú asegurando que no se acumulen pantallas previas.
                navController.navigate("menu/$username") {
                    popUpTo("menu/$username") { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically   // Centra el contenido de la fila.
        ) {

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,    // Flecha hacia atrás.
                    contentDescription = "Regresar",
                    tint = MaterialTheme.colorScheme.primary   // Color del ícono.
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Mi Perfil",                            // Título de pantalla.
                style = MaterialTheme.typography.titleLarge,   // Estilo grande.
                fontWeight = FontWeight.Bold                   // Negrita.
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        // Información institucional
        PerfilCard(title = "Información institucional") {
            // Campo mostrado en modo lectura
            PerfilField(label = "Usuario", value = usuarioState.usuario)

            PerfilField(label = "Correo institucional", value = usuarioState.correoInstitucional)

            PerfilField(label = "Contraseña", value = usuarioState.contrasena)
            // Estos datos vienen solo del servidor o del ViewModel.
        }


        // Datos personales (editable)
        PerfilCard(title = "Datos personales") {

            OutlinedTextField(
                value = usuarioState.nombre,                    // Nombre actual.
                onValueChange = { viewModel.actualizarNombre(it) }, // Actualiza en ViewModel.
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.primerApellido,
                onValueChange = { viewModel.actualizarPrimerApellido(it) },
                label = { Text("Primer apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.segundoApellido,
                onValueChange = { viewModel.actualizarSegundoApellido(it) },
                label = { Text("Segundo apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.fechaNacimiento,
                onValueChange = { viewModel.actualizarFechaNacimiento(it) },
                label = { Text("Fecha de nacimiento") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.estadoNacimiento,
                onValueChange = { viewModel.actualizarEstadoNacimiento(it) },
                label = { Text("Estado de nacimiento") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.sexo,
                onValueChange = { viewModel.actualizarSexo(it) },
                label = { Text("Sexo") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        // Identificadores oficiales
        PerfilCard(title = "Identificadores") {

            OutlinedTextField(
                value = usuarioState.curp,
                onValueChange = { viewModel.actualizarCurp(it) },
                label = { Text("CURP") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.nss,
                onValueChange = { viewModel.actualizarNss(it) },
                label = { Text("Número de Seguridad Social") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        // Contacto
        PerfilCard(title = "Contacto") {

            OutlinedTextField(
                value = usuarioState.telefono,
                onValueChange = { viewModel.actualizarTelefono(it) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = usuarioState.correoAlternativo,
                onValueChange = { viewModel.actualizarCorreoAlternativo(it) },
                label = { Text("Correo alternativo") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}



@Composable
fun PerfilCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    // Contenedor reutilizable con estilo de tarjeta para agrupar secciones del perfil.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            content() // Inserta el contenido específico pasado como parámetro.
        }
    }
}



@Composable
fun PerfilField(label: String, value: String) {
    // Campo de lectura simple compuesto por etiqueta y valor.
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(
            text = if (value.isEmpty()) "—" else value, // Muestra guion si no hay datos.
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    // Crea un NavController simulado para la vista previa.
    val navController = rememberNavController()

    PerfilScreen(
        navController = navController,
        username = "UsuarioDemo",
        onLogout = {},
        onMenuPrincipal = {}
    )
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



