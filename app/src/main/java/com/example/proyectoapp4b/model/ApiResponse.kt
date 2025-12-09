package com.example.proyectoapp4b.model

data class ApiResponse<T>(
    val status: Boolean,
    val message: String? = null,
    val data: T? = null
)
