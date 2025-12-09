package com.example.proyectoapp4b.viewmodel
// Paquete que contiene los ViewModels de la aplicación.
// "viewmodel" indica que pertenece a la capa de presentación (MVVM).

import androidx.lifecycle.ViewModel
// Clase base de Android para ViewModels.
// Permite manejar lógica y estado desacoplado de la UI, sobreviviendo a cambios de configuración.

import com.example.proyectoapp4b.model.Cuatrimestre
// Importa el modelo de datos Cuatrimestre.
// Representa la información académica de un periodo escolar.

import kotlinx.coroutines.flow.MutableStateFlow
// Flujo mutable que permite emitir y actualizar listas de cuatrimestres.
// Ideal para manejar estado reactivo en Compose.

import kotlinx.coroutines.flow.StateFlow
// Versión inmutable del flujo expuesto a la UI.
// Garantiza que la UI solo observe y no modifique directamente el estado.

/**
 * ViewModel encargado de manejar el historial académico del usuario.
 *
 * Responsabilidad: exponer una lista de objetos [Cuatrimestre] que representan
 * los periodos cursados por el estudiante.
 *
 * Patrón: MVVM. La UI observa el flujo `historial` y se actualiza automáticamente
 * cuando cambian los datos.
 */
class HistorialAcademicoViewModel : ViewModel() {

    /** Estado interno mutable que contiene la lista de cuatrimestres. */
    private val _historial = MutableStateFlow<List<Cuatrimestre>>(emptyList())
    // Inicialmente vacío, se llena en el bloque init.

    /** Estado expuesto a la UI como flujo inmutable. */
    val historial: StateFlow<List<Cuatrimestre>> = _historial
    // La UI puede observar este flujo con collectAsState() en Compose.

    init {
        // Inicialización del historial con datos de ejemplo.
        // Cada objeto Cuatrimestre representa un periodo académico con sus atributos.
        _historial.value = listOf(
            Cuatrimestre(
                numero = 4, // Número de cuatrimestre.
                periodo = "Sep - Dic 2025", // Periodo escolar.
                estado = "Activo", // Estado actual del cuatrimestre.
                carrera = "Desarrollo de Software Multiplataforma", // Carrera asociada.
                grupo = "4B Matutino", // Grupo asignado.
                tutor = "Dra. Gricelda Rodriguez Robledo", // Tutor responsable.
                progreso = 49, // Progreso porcentual del cuatrimestre.
                desempeno = "Por capturar" // Desempeño aún no registrado.
            ),
            Cuatrimestre(
                numero = 3,
                periodo = "May - Ago 2025",
                estado = "Finalizado",
                carrera = "Tecnologías de la Información",
                grupo = "3B Matutino",
                tutor = "M.G.T.I. Omar Ordoñez Toledo",
                progreso = 100,
                desempeno = "A - Autónomo"
            ),
            Cuatrimestre(
                numero = 2,
                periodo = "Ene - Abr 2025",
                estado = "Finalizado",
                carrera = "Tecnologías de la Información",
                grupo = "2B Matutino",
                tutor = "Dra. Olga Leticia Robles Garcia",
                progreso = 100,
                desempeno = "A - Autónomo"
            ),
            Cuatrimestre(
                numero = 1,
                periodo = "Sep - Dic 2024",
                estado = "Finalizado",
                carrera = "Tecnologías de la Información",
                grupo = "1B Matutino",
                tutor = "M.G.T.I. Gerardo Chavez Hernandez",
                progreso = 100,
                desempeno = "A - Autónomo"
            )
        )
    }
}

/**
 * Relación con el proyecto:
 *
 * - **Modelo:** Depende de la clase `Cuatrimestre` del paquete `model`, que define
 *   la estructura de cada periodo académico.
 * - **UI/Compose:** La pantalla de historial académico observa `historial: StateFlow<List<Cuatrimestre>>`
 *   y renderiza dinámicamente la lista de cuatrimestres.
 * - **Arquitectura:** Forma parte de la capa de presentación (MVVM). Encapsula el estado
 *   y lo expone de manera inmutable para garantizar seguridad y consistencia.
 * - **Navegación:** Se integra en el flujo de historial académico, permitiendo que el usuario
 *   consulte periodos pasados y el actual.
 * - **Extensibilidad:** Aunque actualmente carga datos estáticos, está preparado para conectarse
 *   con un repositorio que obtenga información real desde una base de datos o API.
 */