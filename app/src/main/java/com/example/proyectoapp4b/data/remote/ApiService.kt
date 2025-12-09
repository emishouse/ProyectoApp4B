package com.example.proyectoapp4b.data.remote

import com.example.proyectoapp4b.model.ApiResponse
import com.example.proyectoapp4b.model.Usuario
import com.example.proyectoapp4b.model.Cuatrimestre
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // LOGIN REAL → casi siempre es POST
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("matricula") matricula: String,
        @Field("password") password: String
    ): ApiResponse<Usuario>

    // HISTORIAL
    @GET("historial/{matricula}")
    suspend fun getHistorial(
        @Path("matricula") matricula: String
    ): ApiResponse<List<Cuatrimestre>>

    @POST("recuperar")
    suspend fun recuperarContrasena(
        @Query("matricula") matricula: String
    ): Response<Unit>

}

