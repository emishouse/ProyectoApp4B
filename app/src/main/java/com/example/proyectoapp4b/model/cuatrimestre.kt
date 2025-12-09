package com.example.proyectoapp4b.model

data class Cuatrimestre(
    val numero: Int? = null,          // identificador único del cuatrimestre
    val periodo: String? = null,      // Ej: "Sep - Dic 2025"
    val estado: String? = null,       // "Activo" / "Finalizado"
    val carrera: String? = null,      // nombre de la carrera
    val grupo: String? = null,        // Ej: "4B Matutino"
    val tutor: String? = null,        // Nombre del tutor
    val progreso: Int? = null,        // porcentaje 0-100
    val desempeno: String? = null     // Ej: "Por capturar", "A - Autónomo"
)
