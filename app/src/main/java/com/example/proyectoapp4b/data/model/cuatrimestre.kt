package com.example.proyectoapp4b.data.model

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

/* =============================================================================
   ¿CÓMO SE RELACIONA ESTE ARCHIVO CON EL RESTO DEL PROYECTO SIGO?
   =============================================================================

1) ApiService (Retrofit)
-------------------------
- Las respuestas del endpoint del historial devuelven:
      ApiResponse<List<Cuatrimestre>>

2) SigoRepositoryImpl
----------------------
- Interpreta response.data como una lista de Cuatrimestre.
- Si la respuesta es válida, regresa esta lista al ViewModel.
- Si no, regresa emptyList().

3) ViewModel del Historial
---------------------------
- Almacena la lista de Cuatrimestre en un estado (LiveData/StateFlow).
- La UI observa estos cambios para actualizar la pantalla.

4) Pantalla de Historial (Compose)
----------------------------------
- Muestra cada cuatrimestre en tarjetas o listas.
- Usa propiedades como:
      periodo
      estado
      progreso
      desempeno
      grupo
      tutor

5) Flujo completo
------------------
Servidor → ApiService → ApiResponse<List<Cuatrimestre>>
→ SigoRepositoryImpl → ViewModel → Pantalla Historial

6) Beneficio dentro del proyecto
--------------------------------
✔ Modelo simple y claro
✔ Facilita parseo automático de JSON con Retrofit/Gson
✔ Uso directo en listas, tarjetas y pantallas del historial
✔ Evita estructuras redundantes en la UI

============================================================================= */

