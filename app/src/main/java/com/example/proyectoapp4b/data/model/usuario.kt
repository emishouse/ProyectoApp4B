package com.example.proyectoapp4b.data.model

/**
 * Representa la información personal y académica básica del usuario autenticado.
 *
 * Todos los datos provienen del backend después del proceso de login y sirven para
 * identificar al estudiante dentro de la aplicación.
 *
 * @property nombre Nombre del usuario.
 * @property primerApellido Primer apellido del usuario.
 * @property segundoApellido Segundo apellido del usuario.
 * @property fechaNacimiento Fecha de nacimiento del estudiante.
 * @property estadoNacimiento Estado donde nació.
 * @property sexo Sexo del usuario.
 * @property curp CURP oficial del estudiante.
 * @property nss Número de Seguro Social.
 * @property telefono Teléfono de contacto.
 * @property correoAlternativo Correo personal del usuario.
 * @property correoInstitucional Correo asignado por la institución.
 * @property usuario Matrícula o ID institucional.            // Matrícula o ID UTM
 * @property perfil Tipo de perfil (ej.: "Estudiante", "Tutor", "Administrador").
 * @property estatusPerfil Estado actual del perfil (ej.: "Activo", "Incompleto", "Bloqueado").
 */
data class Usuario(
    val nombre: String? = null,
    val primerApellido: String? = null,
    val segundoApellido: String? = null,
    val fechaNacimiento: String? = null,
    val estadoNacimiento: String? = null,
    val sexo: String? = null,
    val curp: String? = null,
    val nss: String? = null,
    val telefono: String? = null,
    val correoAlternativo: String? = null,
    val correoInstitucional: String? = null,
    val usuario: String? = null,            // Matrícula o ID UTM
    val perfil: String? = null,
    val estatusPerfil: String? = null
)

/* ================================================================================================
   ¿CÓMO SE RELACIONA ESTA CLASE CON EL RESTO DE LA APP?
   ================================================================================================

   ✔ Es el RESULTADO principal del login.
     - SigoRepository.login() devuelve un Usuario? cuando las credenciales son válidas.
     - Se guarda temporalmente en el ViewModel para usarlo en la sesión.

   ✔ Contiene datos generales que se muestran en varias pantallas:
       - Perfil del estudiante
       - Encabezados en pantallas de historial
       - Información básica dentro de Composables como UserCard o ProfileScreen

   ✔ Funciona como "identificador" del estudiante dentro del sistema.
       - El campo usuario (matrícula) se usa para consultar el historial y otros endpoints:
           SigoRepository.getHistorial(usuario)

   ✔ El ViewModel lo consume para poblar la interfaz y mantener información persistente
     durante toda la sesión del usuario.

   ✔ Las pantallas de UI obtienen estos datos para personalizar la app, por ejemplo:
       - Mostrar nombre completo
       - Mostrar correo institucional
       - Determinar tipo de usuario según perfil

   En resumen:
   La clase Usuario es el modelo central de autenticación y perfil del estudiante.
   Viaja desde la API → Repository → ViewModel → UI, y sirve como base para cargar todo lo
   relacionado al alumno en la aplicación.
   ================================================================================================ */

