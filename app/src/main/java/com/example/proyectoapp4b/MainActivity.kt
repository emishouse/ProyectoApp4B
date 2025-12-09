package com.example.proyectoapp4b
// Paquete raíz de la aplicación. Contiene la clase MainActivity,
// que actúa como punto de entrada principal de la app.

import android.os.Bundle
// Clase que representa el estado guardado de la actividad.
// Se utiliza en el ciclo de vida de Android para restaurar datos.

import androidx.activity.ComponentActivity
// Clase base para actividades que usan Jetpack Compose.
// Proporciona integración con el ciclo de vida y compatibilidad con Compose.

import androidx.activity.compose.setContent
// Función que permite definir el contenido de la actividad usando Composables.

import androidx.compose.material3.MaterialTheme
// Tema de Material Design 3. Define colores, tipografías y estilos globales.

import androidx.compose.material3.Surface
// Contenedor que aplica un color de fondo y eleva el contenido.
// Se usa como base visual para la UI.

import com.example.proyectoapp4b.navigation.AppNavHost
// Importa el NavHost personalizado de la app.
// Encapsula la lógica de navegación entre pantallas.

/**
 * Actividad principal de la aplicación.
 *
 * Responsabilidad: inicializar la UI usando Jetpack Compose y
 * establecer el NavHost como raíz de la navegación.
 *
 * Ciclo de vida: se ejecuta al crear la actividad (`onCreate`).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define el contenido de la actividad usando Compose.
        setContent {
            // Surface: contenedor que aplica el color de fondo definido en el tema.
            Surface(color = MaterialTheme.colorScheme.background) {
                // AppNavHost: componente que gestiona la navegación entre pantallas.
                // Se separó en un archivo independiente para mantener la arquitectura modular.
                AppNavHost()   // 👈 AHORA EL NAVHOST ESTÁ SEPARADO
            }
        }
    }
}

/**
 * Relación con el proyecto:
 *
 * - **UI/Compose:** Es el punto de entrada de la aplicación. Renderiza la UI inicial
 *   y delega la navegación al `AppNavHost`.
 * - **Navegación:** `AppNavHost` contiene el grafo de navegación, separando responsabilidades
 *   y manteniendo la arquitectura limpia.
 * - **Arquitectura:** Forma parte de la capa de presentación. No contiene lógica de negocio,
 *   solo inicializa la UI y conecta con el sistema de navegación.
 * - **Extensibilidad:** Al separar `AppNavHost`, se facilita la integración de nuevas pantallas
 *   sin modificar directamente la actividad principal.
 */



