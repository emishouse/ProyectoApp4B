package com.example.proyectoapp4b.data.repository

import com.example.proyectoapp4b.data.remote.ApiService
import com.example.proyectoapp4b.model.Cuatrimestre
import com.example.proyectoapp4b.model.Usuario

class SigoRepositoryImpl(
    private val api: ApiService
) : SigoRepository {

    override suspend fun login(user: String, pass: String): Usuario? {
        val response = api.login(user, pass)

        if (response.status && response.data != null) {
            return response.data
        }

        return null
    }

    override suspend fun getHistorial(user: String): List<Cuatrimestre> {
        val response = api.getHistorial(user)

        if (response.status && response.data != null) {
            return response.data
        }

        return emptyList()
    }

    override suspend fun recuperarContrasena(matricula: String): Boolean {
        return try {
            val response = api.recuperarContrasena(matricula)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

}

