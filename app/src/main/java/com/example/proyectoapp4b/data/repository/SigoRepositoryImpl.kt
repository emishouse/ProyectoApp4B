package com.example.proyectoapp4b.data.repository

// Importa ApiService, que contiene todas las funciones Retrofit para comunicarse con el servidor.
// Esta clase se inyecta en el repositorio para realizar las solicitudes HTTP.
import com.example.proyectoapp4b.data.remote.ApiService

// Importa el modelo Cuatrimestre, usado para construir la lista del historial académico.
import com.example.proyectoapp4b.model.Cuatrimestre

// Importa el modelo Usuario, que representa al alumno al iniciar sesión.
import com.example.proyectoapp4b.model.Usuario

/**
 * Implementación de la interfaz SigoRepository.
 * -------------------------------------------------
 * Esta clase es la encargada de conectarse directamente con la API utilizando ApiService.
 * Cada función llama al servidor, interpreta la respuesta y regresa los datos limpios al ViewModel.
 *
 * La UI no interactúa con la API ni con Retrofit; únicamente con el repositorio.
 */
class SigoRepositoryImpl(
    private val api: ApiService   // ApiService es inyectado (normalmente mediante Hilt)
) : SigoRepository {

    /**
     * login()
     * ------------------------------------------------------
     * Realiza la solicitud de inicio de sesión llamando al endpoint correspondiente.
     *
     * @param user  Matrícula o usuario.
     * @param pass  Contraseña.
     *
     * @return Usuario?
     *         Devuelve un objeto Usuario si la API responde status = true y data ≠ null.
     *         Devuelve null si las credenciales no son válidas o la API rechaza la petición.
     */
    override suspend fun login(user: String, pass: String): Usuario? {
        val response = api.login(user, pass)

        // Si la API indica éxito y contiene datos, los regresa al ViewModel.
        if (response.status && response.data != null) {
            return response.data
        }

        // Si no coincide el usuario o contraseña, regresa null.
        return null
    }

    /**
     * getHistorial()
     * ------------------------------------------------------
     * Solicita al servidor el historial académico del alumno.
     *
     * @param user  Matrícula del alumno.
     *
     * @return List<Cuatrimestre>
     *         Lista completa de cuatrimestres si la API responde con status = true.
     *         Retorna una lista vacía si ocurre algún error o no hay datos.
     */
    override suspend fun getHistorial(user: String): List<Cuatrimestre> {
        val response = api.getHistorial(user)

        // Si la respuesta fue correcta, se regresa la lista de cuatrimestres.
        if (response.status && response.data != null) {
            return response.data
        }

        // Si no existiera historial, retorna una lista vacía.
        return emptyList()
    }

    /**
     * recuperarContrasena()
     * ------------------------------------------------------
     * Envía una solicitud de recuperación de contraseña al servidor.
     *
     * @param matricula  Matrícula del usuario.
     *
     * @return true si la solicitud fue enviada correctamente (HTTP 200–299),
     *         false si ocurre una excepción o la respuesta no es exitosa.
     *
     * Esta función se usa dentro de RecuperarContrasenaViewModel.
     */
    override suspend fun recuperarContrasena(matricula: String): Boolean {
        return try {
            val response = api.recuperarContrasena(matricula)
            response.isSuccessful
        } catch (e: Exception) {
            // En caso de error de red, JSON inválido o time-out.
            false
        }
    }

}

/* =============================================================================
   ¿CÓMO SE RELACIONA ESTE ARCHIVO CON EL RESTO DEL PROYECTO SIGO?
   =============================================================================

1) SigoRepositoryImpl IMPLEMENTA SigoRepository
------------------------------------------------
- SigoRepositoryImpl es la versión "real" del repositorio.
- Traduce las funciones abstractas en llamadas directas a la API mediante Retrofit.

2) CON ApiService
------------------
- Cada función llama directamente a un endpoint:
      api.login()
      api.getHistorial()
      api.recuperarContrasena()

- ApiService proviene de ApiClient (Retrofit).

3) CON LOS VIEWMODELS
----------------------
- Los ViewModels no llaman a Retrofit.
- Siempre llaman al repositorio:
      LoginViewModel → repository.login()
      HistorialViewModel → repository.getHistorial()
      RecuperarViewModel → repository.recuperarContrasena()

4) CON LA UI
-------------
- La UI nunca toca el repositorio directamente.
- Solo observa LiveData/StateFlow generado por los ViewModels.

5) FLUJO COMPLETO
------------------
Pantalla → ViewModel → SigoRepositoryImpl → ApiService → Servidor
Servidor → ApiService → SigoRepositoryImpl → ViewModel → Pantalla

6) VENTAJAS EN LA ARQUITECTURA
-------------------------------
✔ Aísla las llamadas de red
✔ Código más limpio y mantenible
✔ Fácil de probar
✔ Adaptable si se cambia la API en el futuro

============================================================================= */


