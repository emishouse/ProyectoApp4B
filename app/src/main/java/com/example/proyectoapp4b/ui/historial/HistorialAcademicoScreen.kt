package com.example.proyectoapp4b.ui.historial
// Este package agrupa todas las pantallas relacionadas al historial académico del usuario.



// ---------------------- IMPORTS DOCUMENTADOS UNO POR UNO ----------------------

// Column, Row, Spacer, FillMaxSize, Padding, etc.
import androidx.compose.foundation.layout.*

// Permite recordar un estado de scroll para aplicar desplazamiento manual
import androidx.compose.foundation.rememberScrollState

// Permite hacer scroll vertical a cualquier columna
import androidx.compose.foundation.verticalScroll

// Acceso base al set de íconos por defecto
import androidx.compose.material.icons.Icons

// Ícono de flecha hacia atrás usado para navegación
import androidx.compose.material.icons.filled.ArrowBack

// Componentes principales de Material Design 3 (Text, Icon, Button, Card, etc.)
import androidx.compose.material3.*

// Permite declarar funciones composables de Jetpack Compose
import androidx.compose.runtime.*

// Para alinear verticalmente Row y otros contenedores
import androidx.compose.ui.Alignment

// Modificador para manejar paddings, tamaños, alineaciones, etc.
import androidx.compose.ui.Modifier

// Permite aplicar negrita en textos
import androidx.compose.ui.text.font.FontWeight

// Manejo de unidades dp (píxeles independientes de densidad)
import androidx.compose.ui.unit.dp

// Navegación entre pantallas usando Compose Navigation
import androidx.navigation.NavController

// ViewModel que contiene la lógica y datos del historial académico
import com.example.proyectoapp4b.viewmodel.HistorialAcademicoViewModel

// Permite obtener un ViewModel dentro de un Composable
import androidx.lifecycle.viewmodel.compose.viewModel

// Barra superior personalizada del proyecto (Logout, menú principal, usuario)
import com.example.proyectoapp4b.ui.components.SigoTopBar
// ------------------------------------------------------------------------------



// ========================== PANTALLA HISTORIAL ACADÉMICO ==========================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialAcademicoScreen(
    navController: NavController,              // Controlador que permite navegar entre pantallas
    username: String,                          // Usuario actualmente autenticado
    onLogout: () -> Unit,                      // Función que ejecuta el proceso de cerrar sesión
    onMenuPrincipal: () -> Unit,               // Acción para regresar al menú
    viewModel: HistorialAcademicoViewModel = viewModel()  // ViewModel para la lógica de historial
) {

    // Se suscribe al flujo StateFlow del ViewModel y obtiene la lista de cuatrimestres
    val historial by viewModel.historial.collectAsState()



    // ---------------------------- ESTRUCTURA PRINCIPAL ----------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())    // Permite desplazamiento vertical completo
    ) {

        // ------------------------- BARRA SUPERIOR GLOBAL ---------------------------
        SigoTopBar(
            username = username,
            onLogout = {
                onLogout()     // Acción externa
                navController.navigate("login") {     // Envía al login
                    popUpTo(0) { inclusive = true }   // Limpia el stack de pantallas
                }
            },
            onMenuPrincipal = onMenuPrincipal
        )
        // ----------------------------------------------------------------------------



        Spacer(modifier = Modifier.height(16.dp))



        // ------------------------- TÍTULO Y BOTÓN REGRESAR -------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Historial Académico",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        // -----------------------------------------------------------------------------



        Spacer(modifier = Modifier.height(8.dp))



        // ------------------------ LISTA DE CUATRIMESTRES ----------------------------
        historial.forEach { cuatri ->
            CuatrimestreCard(
                cuatri = cuatri,                // Información del cuatrimestre
                onAddClick = {
                    navController.navigate(
                        "detalleCuatrimestre/${cuatri.numero}/$username"
                    )
                }
            )
        }
        // -----------------------------------------------------------------------------
    }
    // ---------------------------------------------------------------------------------
}
// =======================================================================================




// ====================== BLOQUE FINAL: RELACIÓN CON LO DEMÁS ============================
/*
RELACIÓN DE ESTE ARCHIVO CON EL RESTO DEL PROYECTO:

1. 🔗 Usa el ViewModel:
       → HistorialAcademicoViewModel
   - Desde ahí obtiene la lista de cuatrimestres (historial académico del alumno).

2. 🔗 Se comunica con:
       → CuatrimestreCard
   - Cada cuatrimestre se muestra usando esa tarjeta visual personalizada.

3. 🔗 Participa directamente en la navegación:
       Login → Menu → HistorialAcademicoScreen → DetalleCuatrimestreScreen

4. 🔗 Barra superior global:
       → SigoTopBar
   - Comparte la misma topbar que todas las pantallas principales del proyecto.

5. 🔗 Interacción con NavController:
   - Botón regresar (popBackStack)
   - Botón logout (navController.navigate("login"))
   - Navegación hacia detalle de cada cuatrimestre:
        "detalleCuatrimestre/{numero}/{username}"

6. 🔗 Datos:
   - El ViewModel provee la lista mediante StateFlow.
   - Cada elemento es un objeto Cuatrimestre (proveniente del modelo del historial).

En resumen:
Esta pantalla carga el historial académico completo desde el ViewModel,
lo muestra mediante tarjetas reutilizables,
se integra con la barra superior global
y sirve de puente entre el menú principal y el detalle de cada cuatrimestre.
*/
// =======================================================================================


