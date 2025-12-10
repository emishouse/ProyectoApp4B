package com.example.proyectoapp4b.ui.recuperar
// Paquete que contiene la pantalla de recuperación de contraseña dentro de la capa UI.
// "ui.recuperar" indica que pertenece al flujo de recuperación de credenciales.

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
// Importa componentes de layout (Column, Row, Spacer, etc.) y modificadores de tamaño/espaciado.

import androidx.compose.material.icons.Icons
// Acceso al set de íconos vectoriales por defecto de Material Design.

import androidx.compose.material.icons.filled.ArrowBack
// Ícono específico: flecha hacia atrás, usado en la barra superior para navegación.

import androidx.compose.material3.*
// Importa componentes de Material Design 3 (Scaffold, TopAppBar, Button, Text, etc.).

import androidx.compose.runtime.*
// Importa APIs de estado y composición (remember, mutableStateOf, Composable).

import androidx.compose.ui.Alignment
// Define alineaciones dentro de layouts (ej. CenterHorizontally, Top, etc.).

import androidx.compose.ui.Modifier
// Permite aplicar modificadores a los elementos de UI (padding, fillMaxSize, etc.).

import androidx.compose.ui.graphics.Color
// Clase para definir colores en la UI.

import androidx.compose.ui.text.font.FontWeight
// Define grosor de fuente (ej. Bold).

import androidx.compose.ui.text.style.TextAlign
// Define alineación de texto (ej. Center, Start, End).

import androidx.compose.ui.unit.dp
// Unidad de medida para dimensiones (density-independent pixels).

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarContrasenaScreen(
    onBack: () -> Unit
) {
    var matricula by remember { mutableStateOf("") }
    var mensajeEnviado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recupera tu contraseña") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // 🔹 Fondo del tema
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background // 🔹 Fondo del tema
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // 🔹 Scroll habilitado
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingresa tu nombre de usuario y te enviaremos un correo electrónico con tu contraseña.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { mensajeEnviado = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Enviar correo")
            }

            if (mensajeEnviado) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "✔ Se ha enviado un correo con tu contraseña.",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Relación con el proyecto:
 *
 * - **UI/Compose:** Esta pantalla forma parte del flujo de recuperación de credenciales.
 *   Se conecta con la navegación para regresar (`onBack`) y muestra un formulario simple.
 * - **Estado local:** Usa `remember` y `mutableStateOf` para manejar inputs y feedback
 *   sin necesidad de un ViewModel, ya que la lógica es mínima.
 * - **Navegación:** Se integra en el grafo de navegación; al presionar "Regresar" vuelve
 *   a la pantalla anterior (ej. Login).
 * - **Arquitectura:** Es una pantalla autónoma, pero puede evolucionar para conectarse
 *   con un ViewModel y un repositorio que realmente envíe correos de recuperación.
 * - **Experiencia de usuario:** Refuerza la interacción mostrando un mensaje de confirmación
 *   inmediato tras presionar el botón, simulando el envío de correo.
 */







