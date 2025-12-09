package com.example.proyectoapp4b.data.remote

// Retrofit: librería que permite realizar peticiones HTTP de forma sencilla.
// Hace posible conectarse a APIs REST desde Android.
import retrofit2.Retrofit

// GsonConverterFactory: convierte automáticamente JSON ↔ objetos Kotlin (data classes).
// Evita tener que pasar manualmente las respuestas del servidor.
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * Este objeto Singleton administra la creación y configuración de Retrofit.
 * Su propósito es proporcionar una única instancia de ApiService que será usada en toda la app.
 *
 * - "object" significa que es un Singleton: solo existirá una instancia en toda la ejecución.
 * - Se configura Retrofit con la URL base del servidor y un convertidor JSON.
 */
object ApiClient {

    /**
     * BASE_URL
     * Es la ruta principal donde se encuentran los endpoints del backend.
     * Todas las solicitudes de ApiService partirán de esta URL.
     */
    private const val BASE_URL = "https://TU_API_AQUI/"

    /**
     * apiService
     * - Se crea solo cuando se utiliza por primera vez (lazy).
     * - Retrofit.Builder() configura:
     *      1. baseUrl(BASE_URL): URL principal de la API
     *      2. addConverterFactory(): convierte JSON a objetos Kotlin automáticamente
     *      3. build(): construye el cliente Retrofit
     * - create(ApiService::class.java): genera la implementación real de la interfaz ApiService.
     *
     * Esta instancia se reutiliza en toda la app.
     */
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // convierte JSON automáticamente
            .build()
            .create(ApiService::class.java) // crea la implementación de la interfaz
    }
}

/*
El archivo ApiClient.kt es responsable de crear y configurar el cliente Retrofit que se usa para conectarse al backend SIGO.

Este archivo se relaciona con otros componentes así:

ApiClient → ApiService
ApiClient crea la implementación automática de la interfaz ApiService.
ApiService define los métodos HTTP (GET, POST, etc.) que tu app puede llamar.
ApiClient → Repository (SigoRepositoryImpl)

Los repositorios consumen apiService para obtener datos reales desde el backend.
Ejemplo:
api.login(user, pass)

Repository → ViewModels
Los ViewModels llaman a los repositorios para obtener datos de la API.
ViewModels → UI (Compose Screens)
Las pantallas observan los datos expuestos por ViewModels.
 */

