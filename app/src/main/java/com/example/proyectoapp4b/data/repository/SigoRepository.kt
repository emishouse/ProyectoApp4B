package com.example.proyectoapp4b.data.repository

// Importa el modelo Usuario, que representa los datos del usuario obtenido tras iniciar sesión.
// Este modelo será retornado por la función login().
import com.example.proyectoapp4b.model.Usuario

// Importa el modelo Cuatrimestre, que representa cada periodo del historial académico.
// Es utilizado por la función getHistorial() para devolver la lista de cuatrimestres.
import com.example.proyectoapp4b.model.Cuatrimestre

/**
 * Interfaz SigoRepository
 * ------------------------
 * Esta interfaz define todas las operaciones que el repositorio del proyecto SIGO debe ofrecer.
 * Funciona como una capa intermedia entre los ViewModels y la fuente de datos (API o base local).
 *
 * Las funciones son `suspend` porque se ejecutarán dentro de corrutinas, evitando bloquear el hilo principal.
 * La lógica real se implementa en la clase SigoRepositoryImpl.
 */
interface SigoRepository {

    /**
     * login()
     * --------
     * Autentica a un usuario en el sistema.
     *
     * @param user   Matrícula o nombre de usuario.
     * @param pass   Contraseña ingresada por el usuario.
     *
     * @return Usuario?  Devuelve un objeto Usuario si las credenciales son correctas,
     *                   o null si la autenticación falla.
     *
     * Esta función será llamada por LoginViewModel y utiliza a ApiService dentro de la implementación.
     */
    suspend fun login(user: String, pass: String): Usuario?

    /**
     * getHistorial()
     * ----------------
     * Obtiene el historial académico del alumno.
     *
     * @param user   Matrícula del alumno.
     *
     * @return List<Cuatrimestre>
     *         Lista de todos los cuatrimestres que tiene registrados el usuario.
     *
     * Esta función alimenta la pantalla de Historial en el ViewModel correspondiente.
     */
    suspend fun getHistorial(user: String): List<Cuatrimestre>

    /**
     * recuperarContrasena()
     * ----------------------
     * Envía la solicitud de recuperación de contraseña al servidor.
     *
     * @param matricula  Matrícula del alumno que solicita el correo de recuperación.
     *
     * @return Boolean
     *         true si la recuperación fue enviada correctamente,
     *         false si algo salió mal o la matrícula no existe.
     *
     * Utilizado por el ViewModel de RecuperarContrasena.
     */
    suspend fun recuperarContrasena(matricula: String): Boolean
}

/* =============================================================================
   ¿CÓMO SE RELACIONA ESTE ARCHIVO CON EL RESTO DEL PROYECTO SIGO?
   =============================================================================

1) CON ApiService
------------------
- SigoRepository no hace llamadas a la API directamente.
- Su implementación (SigoRepositoryImpl) sí lo hace.
- En ella se usan funciones de ApiService como:
      apiService.login(...)
      apiService.getHistorial(...)
      apiService.recuperarContrasena(...)

2) CON ApiClient
------------------
- ApiClient es quien construye Retrofit.
- ApiClient proporciona la instancia de ApiService.
- Esta instancia se inyecta en SigoRepositoryImpl.

3) CON ViewModels
------------------
- Los ViewModels NUNCA llaman directamente a Retrofit.
- En su lugar llaman a SigoRepository para solicitar datos.
- Ejemplos:
      LoginViewModel → repository.login()
      HistorialViewModel → repository.getHistorial()
      RecuperarContrasenaViewModel → repository.recuperarContrasena()

4) CON LA UI (Compose)
-----------------------
- Las pantallas observan los estados creados por los ViewModels.
- Cuando la UI necesita información del servidor, lo pide así:
      UI → ViewModel → SigoRepository → ApiService → Servidor

5) BENEFICIO EN LA ARQUITECTURA
-------------------------------
Usar SigoRepository permite:
✔ Separar lógica de datos y lógica de UI
✔ Cambiar ApiService o la API completa sin modificar ninguna pantalla
✔ Hacer pruebas unitarias mucho más fácil
✔ Mantener el código limpio, modular y escalable

En resumen:
-----------
Este archivo es el PUENTE que conecta los ViewModels con la API, sin que la UI dependa directamente
de las llamadas de red. Es una pieza clave dentro de la arquitectura MVVM del proyecto SIGO.

============================================================================= */


