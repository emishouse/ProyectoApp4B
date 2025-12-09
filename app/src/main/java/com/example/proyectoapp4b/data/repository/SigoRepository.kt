package com.example.proyectoapp4b.data.repository

import com.example.proyectoapp4b.model.Usuario
import com.example.proyectoapp4b.model.Cuatrimestre

interface SigoRepository {

    suspend fun login(user: String, pass: String): Usuario?

    suspend fun getHistorial(user: String): List<Cuatrimestre>

    suspend fun recuperarContrasena(matricula: String): Boolean
}
