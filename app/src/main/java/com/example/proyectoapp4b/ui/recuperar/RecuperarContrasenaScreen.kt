package com.example.proyectoapp4b.ui.recuperar
// Paquete que contiene la pantalla de recuperación de contraseña dentro de la capa UI.
// "ui.recuperar" indica que pertenece al flujo de recuperación de credenciales.

import androidx.compose.foundation.layout.*
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
// Opt-in para usar APIs experimentales de Material3 (TopAppBar).
@Composable
        /**
         * Pantalla de recuperación de contraseña.
         *
         * @param onBack Acción que se ejecuta al presionar el botón de regresar.
         *
         * Responsabilidad: mostrar un formulario donde el usuario ingresa su matrícula
         * y simular el envío de un correo con la contraseña.
         */
fun RecuperarContrasenaScreen(
    onBack: () -> Unit
) {
    // Estado local para almacenar la matrícula ingresada.
    var matricula by remember { mutableStateOf("") }

    // Estado local para indicar si el mensaje de confirmación ya fue enviado.
    var mensajeEnviado by remember { mutableStateOf(false) }

    Scaffold(
        // Estructura base de la pantalla: incluye barra superior y contenido.
        topBar = {
            TopAppBar(
                title = { Text("Recupera tu contraseña") },
                navigationIcon = {
                    // Botón de navegación para regresar a la pantalla anterior.
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White, // Fondo blanco en el TopAppBar.
                    titleContentColor = Color.Black, // Texto negro en el título.
                    navigationIconContentColor = Color.Black // Ícono negro.
                )
            )
        },
        containerColor = Color.White // Fondo blanco en todo el Scaffold.
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding) // Respeta padding interno del Scaffold.
                .padding(24.dp) // Margen adicional alrededor del contenido.
                .fillMaxSize(), // Ocupa todo el espacio disponible.
            verticalArrangement = Arrangement.Top, // Elementos alineados arriba.
            horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal.
        ) {

            Text(
                text = "Ingresa tu nombre de usuario y te enviaremos un correo electrónico con tu contraseña.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center // Texto centrado.
            )

            Spacer(modifier = Modifier.height(24.dp)) // Espacio entre elementos.

            OutlinedTextField(
                value = matricula, // Valor actual del campo.
                onValueChange = { matricula = it }, // Actualiza estado al escribir.
                label = { Text("Matrícula") }, // Etiqueta del campo.
                modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho.
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Simula envío de correo: activa mensaje de confirmación.
                    mensajeEnviado = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688), // Fondo verde.
                    contentColor = Color.White // Texto blanco.
                )
            ) {
                Text("Enviar correo")
            }

            if (mensajeEnviado) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "✔ Se ha enviado un correo con tu contraseña.",
                    color = MaterialTheme.colorScheme.primary, // Color primario del tema.
                    fontWeight = FontWeight.Bold // Texto en negritas.
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







