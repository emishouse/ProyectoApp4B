package com.example.proyectoapp4b.data.remote

// ApiResponse: clase genérica personalizada donde viene el éxito/error de la API.
import com.example.proyectoapp4b.model.ApiResponse

// Usuario y Cuatrimestre: modelos que representan los datos que envía el servidor.
import com.example.proyectoapp4b.model.Usuario
import com.example.proyectoapp4b.model.Cuatrimestre

// Response: permite obtener información extra del servidor (códigos de estado, headers, etc.).
import retrofit2.Response

// Anotaciones de Retrofit para definir endpoints y parámetros HTTP.
import retrofit2.http.*

/**
 * ApiService
 * Interfaz que define todos los ENDPOINTS que la app utilizará para comunicarse con la API.
 *
 * Retrofit genera automáticamente la implementación real de esta interfaz,
 * permitiendo realizar peticiones HTTP sin escribir código manual de conexión.
 */
interface ApiService {

    /**
     * LOGIN
     * • Se usa @FormUrlEncoded porque el servidor espera el formato:
     *      matricula=XXXX&password=YYYY
     * • @POST("login"): indica que es una petición POST al endpoint /login
     * • @Field: envía los parámetros dentro del cuerpo de la forma "form-data"
     *
     * Responde con:
     * ApiResponse<Usuario> → donde viene el usuario y la información del login.
     */
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("matricula") matricula: String,
        @Field("password") password: String
    ): ApiResponse<Usuario>


    /**
     * HISTORIAL ACADÉMICO
     * • @GET("historial/{matricula}"): la matrícula va dentro de la URL
     * • @Path reemplaza {matricula} dinámicamente
     *
     * Devuelve:
     * ApiResponse<List<Cuatrimestre>>
     * → lista de cuatrimestres del alumno.
     */
    @GET("historial/{matricula}")
    suspend fun getHistorial(
        @Path("matricula") matricula: String
    ): ApiResponse<List<Cuatrimestre>>


    /**
     * RECUPERAR CONTRASEÑA
     * • Es un @POST porque realiza una acción (enviar correo).
     * • Usa @Query para enviar la matrícula en la URL:
     *      /recuperar?matricula=XXXX
     *
     * Devuelve:
     * Response<Unit>
     * • Solo indica si la petición fue EXITOSA (código 200)
     *   o si falló (400, 500, etc.)
     */
    @POST("recuperar")
    suspend fun recuperarContrasena(
        @Query("matricula") matricula: String
    ): Response<Unit>



    /*
     * RELACIÓN DE ESTE ARCHIVO CON EL RESTO DEL PROYECTO
     *
     * ApiService es la CAPA MÁS DIRECTA con la API.
     * No se llama desde las pantallas directamente, sino que sigue una arquitectura ordenada:
     *
     * 1. ApiClient ---------------------------------------------
     *      • Crea la instancia REAL de ApiService usando Retrofit.
     *
     * 2. SigoRepositoryImpl ------------------------------------
     *      • Llama a los métodos de ApiService (login, historial, recuperar).
     *      • Centraliza toda la lógica de acceso a datos.
     *
     * 3. ViewModels (LoginViewModel, HistorialViewModel, etc.) --
     *      • Llaman a los repositorios.
     *      • Transforman datos y exponen estados a la UI.
     *
     * 4. Pantallas Compose (LoginScreen, MenuScreen, etc.) -------
     *      • Observan los estados del ViewModel.
     *      • Nunca llaman directamente a ApiService.
     *
     *  ESTRUCTURA FINAL:
     *
     *      UI → ViewModel → Repository → ApiService → Servidor
     *
     * • ApiService NO almacena datos.
     * • NO contiene lógica.
     * • Solo define CÓMO se llaman los endpoints del backend SIGO.
     *
     */
}


