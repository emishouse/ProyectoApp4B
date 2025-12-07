package com.example.proyectoapp4b.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoapp4b.ui.components.SigoTopBar

@Composable
fun PerfilScreen(
    navController: NavController,
    username: String,
    onLogout: () -> Unit,
    onMenuPrincipal: () -> Unit,
    viewModel: PerfilViewModel = viewModel()
) {
    val usuarioState by viewModel.usuario.collectAsState()

    // Cargar datos iniciales
    LaunchedEffect(username) {
        viewModel.cargarPerfil(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 🔵 TopBar reutilizada
        SigoTopBar(
            username = username,
            onLogout = onLogout,
            onMenuPrincipal = onMenuPrincipal
        )

        Spacer(modifier = Modifier.height(24.dp))

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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección: Información institucional
        PerfilCard(title = "Información institucional") {
            PerfilField(label = "Usuario", value = usuarioState.usuario)
            PerfilField(label = "Correo institucional", value = usuarioState.correoInstitucional)
            PerfilField(label = "Contraseña", value = usuarioState.contrasena)
        }

        // Sección: Datos personales
        PerfilCard(title = "Datos personales") {
            OutlinedTextField(
                value = usuarioState.nombre,
                onValueChange = { viewModel.actualizarNombre(it) },
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

        // Sección: Identificadores oficiales
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

        // Sección: Contacto
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun PerfilField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(
            text = if (value.isEmpty()) "—" else value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    val navController = rememberNavController()
    PerfilScreen(
        navController = navController,
        username = "UsuarioDemo",
        onLogout = {},
        onMenuPrincipal = {}
    )
}
