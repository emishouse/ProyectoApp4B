package com.example.proyectoapp4b.model

/**
 * Clase que representa el detalle completo de una materia dentro del historial
 * académico del estudiante.
 *
 * @property nombre Nombre de la materia.
 * @property docente Nombre del profesor que imparte la asignatura.
 * @property progreso Porcentaje de avance mostrado al estudiante (ej.: "80%").
 * @property evaluacion Calificación final o estatus de evaluación (ej.: "9.5", "Pendiente").
 * @property desempeno Nivel de desempeño asignado (ej.: "A - Autónomo", "Por capturar").
 * @property unidades Lista de unidades o temas cubiertos en la materia.
 */
data class MateriaDetalle(
    val nombre: String,
    val docente: String,
    val progreso: String,
    val evaluacion: String,
    val desempeno: String,
    val unidades: List<String>
)

/* ================================================================================================
   ¿CÓMO SE RELACIONA ESTA CLASE CON EL RESTO DE LA APP?
   ================================================================================================

   ✔ Se utiliza dentro del módulo de historial académico del estudiante.
   ✔ Normalmente forma parte de un objeto más grande (por ejemplo "Cuatrimestre" o "HistorialDetalle")
     cuando el backend envía el detalle de cada materia cursada.
   ✔ Es consumida por el Repository (SigoRepository) cuando se llama a un endpoint que devuelve
     el detalle por materia.
   ✔ Los ViewModel la reciben para poblar las pantallas de composables como:
       - Pantalla de detalle de cuatrimestre
       - Pantalla de detalle de materia
   ✔ Las vistas la usan directamente para mostrar:
       - Nombre de la materia
       - Nombre del docente
       - Progreso del alumno
       - Evaluación obtenida
       - Nivel de desempeño
       - Lista de unidades (generalmente mostradas en un LazyColumn)

   En resumen:
   Esta clase sirve como *modelo de datos* para representar la información completa de una materia
   dentro del historial académico del estudiante, viajando desde la API → Repository → ViewModel → UI.
   ================================================================================================ */

