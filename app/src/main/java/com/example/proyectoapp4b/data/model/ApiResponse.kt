package com.example.proyectoapp4b.data.model

/**
 * Clase ApiResponse<T>
 * --------------------------------------------------------------------
 * Representa el formato estándar de respuesta que devuelve el servidor
 * para todas las peticiones del proyecto SIGO.
 *
 * Es una clase genérica (usa <T>) para poder adaptarse a cualquier tipo
 * de dato que el backend envíe: Usuario, Cuatrimestre, listas, textos, etc.
 *
 * Campos:
 *  - status: Indica si la operación fue exitosa (true/false).
 *  - message: Mensaje opcional que envía el servidor (puede ser error o aviso).
 *  - data: Contenido de la respuesta, cuyo tipo depende de T.
 */
data class ApiResponse<T>(

    /**
     * true  -> La petición fue correcta y el servidor procesó los datos.
     * false -> Hubo algún error (credenciales incorrectas, matrícula no existe,
     *          error interno del servidor, etc.).
     */
    val status: Boolean,

    /**
     * Mensaje informativo enviado desde el servidor.
     * Puede contener:
     *    - Descripción del error
     *    - Confirmación de operación
     *    - Observaciones
     *
     * Es opcional y puede venir nulo.
     */
    val message: String? = null,

    /**
     * Datos devueltos por la petición.
     * Su tipo T puede ser:
     *    Usuario
     *    List<Cuatrimestre>
     *    Boolean
     *    Cualquier otro objeto definido en la API
     *
     * Puede ser null cuando:
     *    - La operación falla
     *    - No hay datos asociados a la respuesta
     */
    val data: T? = null
)

/* =============================================================================
   ¿CÓMO SE RELACIONA ESTE ARCHIVO CON EL RESTO DEL PROYECTO SIGO?
   =============================================================================

1) ApiService (Retrofit)
-------------------------
- Cada endpoint devuelve un ApiResponse<T>.
- Ejemplos:
      login() -> ApiResponse<Usuario>
      getHistorial() -> ApiResponse<List<Cuatrimestre>>

2) SigoRepositoryImpl
----------------------
- El repositorio analiza los campos:
      response.status
      response.data
      response.message

- Decide si entregar o no los datos al ViewModel.

3) ViewModels
--------------
- Usan la estructura ApiResponse para saber si la llamada fue exitosa.
- Actualizan estados como "loading", "error" o resultados con base en:
      response.status
      response.message

4) UI (Compose)
----------------
- Muestra mensajes de error usando response.message
- Muestra datos si response.data ≠ null.

5) Arquitectura
----------------
- ApiResponse garantiza que TODA respuesta del servidor sigue un formato único.
- Esto facilita:
      ✔ Manejo de errores
      ✔ Lectura uniforme de estructuras
      ✔ Reducción de código repetido
      ✔ Mayor claridad en el repositorio y ViewModels

============================================================================= */

