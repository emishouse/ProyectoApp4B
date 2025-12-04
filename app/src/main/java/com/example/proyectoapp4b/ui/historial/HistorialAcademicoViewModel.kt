package com.example.proyectoapp4b.ui.historial

import androidx.lifecycle.ViewModel
import com.example.proyectoapp4b.model.Cuatrimestre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistorialAcademicoViewModel : ViewModel() {

    private val _historial = MutableStateFlow<List<Cuatrimestre>>(emptyList())
    val historial: StateFlow<List<Cuatrimestre>> = _historial

    init {
        _historial.value = listOf(
            Cuatrimestre(
                numero = 4,
                periodo = "Sep - Dic 2025",
                estado = "Activo",
                carrera = "Desarrollo de Software Multiplataforma",
                grupo = "4B Matutino",
                tutor = "Dra. Gricelda Rodriguez Robledo",
                progreso = 49,
                desempeno = "Por capturar"
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