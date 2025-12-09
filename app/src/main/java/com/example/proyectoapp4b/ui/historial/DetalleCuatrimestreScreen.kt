package com.example.proyectoapp4b.ui.historial
// Este package agrupa todas las pantallas relacionadas al historial académico del usuario.


// ---------------------- IMPORTS DOCUMENTADOS UNO POR UNO ----------------------

// Contenedores básicos: Column, Row, Spacer, Box, FillMaxSize, etc.
import androidx.compose.foundation.layout.*

// Lista vertical eficiente que solo renderiza los elementos visibles
import androidx.compose.foundation.lazy.LazyColumn

// Permite iterar automáticamente una lista dentro de un LazyColumn
import androidx.compose.foundation.lazy.items

// Acceso general al set de íconos predeterminados de Material Design
import androidx.compose.material.icons.Icons

// Ícono en forma de flecha hacia atrás (para navegación)
import androidx.compose.material.icons.filled.ArrowBack

// Componentes principales de Material Design 3 (Scaffold, Card, Text, Icon, etc.)
import androidx.compose.material3.*

// Permite declarar funciones composables que construyen UI en Jetpack Compose
import androidx.compose.runtime.Composable

// Para manejar modificadores visuales como tamaño, alineación o padding
import androidx.compose.ui.Modifier

// Alineación vertical de componentes dentro de un Row
import androidx.compose.ui.Alignment

// Para aplicar peso tipográfico como Bold (negrita)
import androidx.compose.ui.text.font.FontWeight

// Para manejar valores en dp (density-independent pixels)
import androidx.compose.ui.unit.dp

// Controlador de navegación que permite regresar, avanzar o cambiar rutas
import androidx.navigation.NavController

// Barra superior personalizada del proyecto (con logout y menú principal)
import com.example.proyectoapp4b.ui.components.SigoTopBar
// ------------------------------------------------------------------------------



// ========================== MODELO PARA MATERIAS DETALLADAS ==========================
data class MateriaDetalle(
    val nombre: String,
    val docente: String,
    val progreso: String,
    val evaluacion: String,
    val desempeno: String,
    val unidades: List<String>
)
// Este modelo representa cada materia dentro de un cuatrimestre específico.
// =====================================================================================



// ============================= PANTALLA PRINCIPAL ====================================
@Composable
fun DetalleCuatrimestreScreen(
    navController: NavController,     // Controlador de navegación para moverse entre pantallas
    numero: Int,                      // Número del cuatrimestre seleccionado
    username: String,                 // Usuario actual (se muestra en la topbar)
    onLogout: () -> Unit,             // Acción a ejecutar cuando el usuario cierra sesión
    onMenuPrincipal: () -> Unit       // Acción para regresar al menú principal
) {

    // ------------------------- DATOS DE EJEMPLO (HARDCODEADOS) -------------------------
    // En una versión final, estos datos serían obtenidos desde la API a través de ViewModel.
    val materias = listOf(
        MateriaDetalle(
            nombre = "Inglés IV",
            docente = "Lic. Marco Antonio Suarez Villanueva",
            progreso = "50%",
            evaluacion = "--",
            desempeno = "Por capturar",
            unidades = listOf(
                "El pasado (Estratégico)",
                "Pasado Simple vs. Pasado Continuo (Por capturar)"
            )
        ),
        MateriaDetalle(
            nombre = "Ética Profesional",
            docente = "Lic. Laura Perez Pazos",
            progreso = "66%",
            evaluacion = "--",
            desempeno = "Por capturar",
            unidades = listOf("Introducción a la Ética (Estratégico)")
        )
    )
    // -----------------------------------------------------------------------------------


    // ------------------------------- ESTRUCTURA GENERAL --------------------------------
    Scaffold(
        topBar = {
            SigoTopBar(
                username = username,           // Muestra el nombre del alumno
                onLogout = {
                    onLogout()                 // Acción personalizada definida por el caller
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }  // Limpia el stack y regresa al login
                    }
                },
                onMenuPrincipal = onMenuPrincipal  // Regresar al menú principal
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)        // Respeta el espacio reservado por Scaffold
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))


            // ------------------------------ ENCABEZADO SUPERIOR -------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,        // Flecha para regresar
                        contentDescription = "Regresar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Detalle del ${numero}º cuatrimestre",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            // -----------------------------------------------------------------------------


            Spacer(modifier = Modifier.height(8.dp))


            // --------------------------- LISTA DE MATERIAS ------------------------------
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(materias) { materia ->
                    MateriaCard(materia)     // Renderiza una Card por materia
                }
            }
            // -----------------------------------------------------------------------------
        }
    }
    // -----------------------------------------------------------------------------------
}




// ============================== CARD DE CADA MATERIA =================================
@Composable
fun MateriaCard(materia: MateriaDetalle) {
    Card(
        modifier = Modifier
            .padding(12.dp)       // Separación entre tarjetas
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp) // Sombra ligera estilo Material
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(materia.nombre, style = MaterialTheme.typography.titleMedium)

            Text(
                "Docente: ${materia.docente}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(8.dp))

            Text("Progreso: ${materia.progreso}")
            Text("Evaluación: ${materia.evaluacion}")
            Text("Desempeño: ${materia.desempeno}")

            Spacer(Modifier.height(8.dp))

            Text(
                "Unidades Temáticas:",
                style = MaterialTheme.typography.labelMedium
            )

            materia.unidades.forEach { unidad ->
                Text("- $unidad", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
// =======================================================================================



// ====================== BLOQUE FINAL: RELACIÓN CON LO DEMÁS ============================
/*
RELACIÓN DE ESTE ARCHIVO CON EL RESTO DEL PROYECTO:

1. 🔗 Se relaciona directamente con:
       → CuatrimestreCard
       → Cuatrimestre model (Cuatrimestre.kt)
   - Desde una lista de Cuatrimestres, cuando el usuario selecciona uno,
     se navega hacia *DetalleCuatrimestreScreen* pasando el número del cuatri.

2. 🔗 Usa el componente global:
       → SigoTopBar
   - Para mostrar usuario, menú principal y botón de cerrar sesión.

3. 🔗 Interactúa con:
       → NavController
   - Se usa para regresar a la pantalla anterior.
   - También se utiliza para mandar al login cuando se hace logout.

4. 🔗 En una versión real, las materias provendrían de:
       → APIResponse<List<MateriaDetalle>>
       → ViewModel especializado (HistorialViewModel)
   - Actualmente los datos están *mockeados*, pero el flujo ya está preparado.

5. 🔗 Flujo completo donde participa:
       Login → Home → Historial → ListaCuatris → DetalleCuatrimestreScreen

6. 🔗 También se conecta con:
       - MateriaCard → para imprimir cada materia
       - Modelos del historial académico (Cuatrimestre, MateriaDetalle)

En resumen:
Esta pantalla muestra el detalle completo de un cuatrimestre,
recibe datos desde el flujo de historial,
usa la topbar global,
y sirve como puente hacia la gestión de materias y unidades.
*/
// =======================================================================================




