package com.example.proyectoapp4b.model

data class Cuatrimestre(
    val numero: Int,          // 👈 identificador único del cuatrimestre
    val periodo: String,      // Ej: "Sep - Dic 2025"
    val estado: String,       // Ej: "Activo" o "Finalizado"
    val carrera: String,      // Ej: "Desarrollo de Software Multiplataforma"
    val grupo: String,        // Ej: "4B Matutino"
    val tutor: String,        // Nombre del tutor
    val progreso: Int,        // Porcentaje de avance (0-100)
    val desempeno: String     // Ej: "Por capturar" o "A - Autónomo"
)