package com.example.proyectoapp4b.ui.historial
// Este package indica que esta pantalla pertenece al módulo UI-historial,
// que agrupa las pantallas relacionadas con el historial académico del usuario.


// ---------------------- IMPORTS DOCUMENTADOS UNO POR UNO ----------------------

// Layouts básicos: Column, Row, Spacer, Box, etc.
import androidx.compose.foundation.layout.*

// Acceso al contenedor general de íconos predeterminados de Material
import androidx.compose.material.icons.Icons

// Ícono específico de tipo "Add" (representado como un signo de +)
import androidx.compose.material.icons.filled.Add

// Componentes de Material Design 3 (Card, Text, Icon, IconButton, etc.)
import androidx.compose.material3.*

// Permite que esta función sea usada como un Composable de Jetpack Compose
import androidx.compose.runtime.Composable

// Clase principal para modificar elementos visuales (size, padding, fill, etc.)
import androidx.compose.ui.Modifier

// Para manejar colores personalizados en el ícono o textos
import androidx.compose.ui.graphics.Color

// Para aplicar estilos de texto como Bold, Medium, Light, etc.
import androidx.compose.ui.text.font.FontWeight

// Para definir valores en dp (density-independent pixels)
import androidx.compose.ui.unit.dp

// Modelo de datos Cuatrimestre, contiene número, periodo, grupo, tutor, etc.
import com.example.proyectoapp4b.model.Cuatrimestre
// ----------------------------------------------------------------------------------



// =========================== COMPONENTE PRINCIPAL ===============================

@Composable
fun CuatrimestreCard(
    cuatri: Cuatrimestre,            // Objeto que contiene toda la info del cuatrimestre
    onAddClick: () -> Unit = {}      // Callback cuando se presiona el botón "+"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()          // La tarjeta ocupará todo el ancho horizontal disponible
            .padding(8.dp),          // Margen externo para separar cards entre sí
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp  // Sombra ligera para dar efecto de profundidad
        )
    ) {

        // Contenedor vertical interno de la tarjeta
        Column(
            modifier = Modifier.padding(16.dp) // Margen interior; separa contenido del borde
        ) {

            // Título principal con el número del cuatrimestre y su periodo
            Text(
                text = "Cuatrimestre ${cuatri.numero} - ${cuatri.periodo}",
                style = MaterialTheme.typography.titleMedium, // Tamaño medio de título
                fontWeight = FontWeight.Bold                  // Negritas
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espacio vertical para respiración visual



            // -------------------------- INFORMACIÓN GENERAL --------------------------

            Text(text = "Estado: ${cuatri.estado}")                     // Avance académico del cuatrimestre
            Text(text = "Carrera: ${cuatri.carrera}")                   // Carrera a la que pertenece
            Text(text = "Grupo: ${cuatri.grupo}")                       // Grupo asignado
            Text(text = "Tutor: ${cuatri.tutor}")                       // Nombre del tutor del cuatri
            Text(text = "Progreso: ${cuatri.progreso}%")                // Avance del cuatri (porcentaje)
            Text(text = "Desempeño: ${cuatri.desempeno}")               // Rendimiento general del alumno

            // -------------------------------------------------------------------------


            Spacer(modifier = Modifier.height(12.dp)) // Separación previa al botón "+"


            // ----------------------- BOTÓN PARA AGREGAR MATERIAS ----------------------

            Row(
                modifier = Modifier.fillMaxWidth(),                      // Ocupa todo el ancho
                horizontalArrangement = Arrangement.End                  // Alineado a la derecha
            ) {

                IconButton(
                    onClick = onAddClick,                                // Ejecuta el callback recibido
                    modifier = Modifier.size(40.dp)                      // Tamaño del botón
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,                 // Ícono de "+"
                        contentDescription = "Agregar materia",
                        tint = Color(0xFF009688)                         // Color verde estilo Material
                    )
                }
            }
            // -------------------------------------------------------------------------
        }
    }
}


// ====================== BLOQUE FINAL: RELACIÓN CON LO DEMÁS ======================
/*
RELACIÓN DE ESTE ARCHIVO CON EL RESTO DEL PROYECTO:

1. 🔗 Se relaciona con el modelo:
       → com.example.proyectoapp4b.model.Cuatrimestre
   - Este componente recibe un objeto "Cuatrimestre".
   - Usa sus atributos: numero, periodo, estado, carrera, grupo, tutor, progreso, desempeno.

2. 🔗 Se usa dentro de:
       → Pantallas del historial académico (HistorialScreen o ListaCuatrisScreen)
   - Normalmente se imprimen varias tarjetas (una por cada cuatrimestre del alumno).

3. 🔗 Interactúa con:
       → onAddClick()
   - Este callback permite navegar a otra pantalla,
     por ejemplo “AgregarMateriaScreen”, o abrir un diálogo para registrar materias.

4. 🔗 Es parte del flujo:
       Login → Home → Historial → Lista de Cuatrimestres → [CuatrimestreCard]

5. 🔗 Puede ser combinado con:
       - LazyColumn (para mostrar múltiples cuatrimestres)
       - ViewModel que provee lista de Cuatrimestre desde APIResponse<List<Cuatrimestre>>

6. 🔗 Se conecta indirectamente con la API:
       → ApiResponse<T>
   - La API responde una lista de objetos Cuatrimestre.
   - Esa lista alimenta el composable CuatrimestreCard mediante un ViewModel.

En resumen:
Este archivo es la representación visual de cada cuatrimestre dentro del historial académico,
tomando su información desde los modelos y sirviendo como botón de entrada para más acciones.
*/
