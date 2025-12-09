package com.example.proyectoapp4b.ui.perfil
// Paquete que agrupa las clases relacionadas con la pantalla de perfil dentro del módulo UI.
// Convención: ui.perfil indica que pertenece a la capa de presentación y al flujo de perfil.

import androidx.lifecycle.ViewModel
// Importa la clase base ViewModel del ciclo de vida de Android.
// Proporciona un contenedor para estado/lógica desacoplado de la UI y sobreviviente a cambios de configuración.

import androidx.lifecycle.viewModelScope
// Proporciona un CoroutineScope ligado al ciclo de vida del ViewModel.
// Todas las corrutinas lanzadas aquí se cancelan automáticamente cuando el ViewModel se destruye.

import kotlinx.coroutines.flow.MutableStateFlow
// Flujo observable y mutable para manejar estado reactivo.
// Permite emitir valores y notificar suscriptores (UI) cuando cambian.

import kotlinx.coroutines.flow.StateFlow
// Versión inmutable del flujo expuesto a la UI.
// Garantiza que la UI sólo observe y no modifique el estado directamente.

import kotlinx.coroutines.launch
// Función de extensión para iniciar corrutinas dentro de un CoroutineScope.
// Se usa para ejecutar tareas asíncronas (I/O, repositorio, etc.) sin bloquear la UI.

// Renombrada para no chocar con model.Usuario

/**
 * Data class que modela el estado completo del perfil del usuario en la capa de presentación.
 *
 * Propósito: encapsular todos los campos del perfil para que la UI consuma un solo objeto inmutable.
 * Diseño: cada campo tiene un valor por defecto (cadena vacía) para evitar null-safety issues en la UI.
 *
 * @property usuario Identificador único de la cuenta dentro de la app (username).
 * @property correoInstitucional Correo institucional asociado al usuario (dominio escolar).
 * @property contrasena Contraseña en texto plano (solo en estado de UI; no debe persistirse así).
 * @property nombre Nombre de pila del usuario.
 * @property primerApellido Primer apellido del usuario.
 * @property segundoApellido Segundo apellido del usuario.
 * @property fechaNacimiento Fecha de nacimiento representada como String (formato pendiente/definible).
 * @property estadoNacimiento Estado de la república donde nació el usuario.
 * @property sexo Sexo del usuario; convención de valores definida por la app (ej. "M", "F").
 * @property curp Clave Única de Registro de Población (CURP).
 * @property nss Número de Seguro Social (NSS).
 * @property telefono Teléfono de contacto principal del usuario.
 * @property correoAlternativo Correo secundario para recuperación/alertas.
 */
data class UsuarioPerfil(
    val usuario: String = "",
    val correoInstitucional: String = "",
    val contrasena: String = "",
    val nombre: String = "",
    val primerApellido: String = "",
    val segundoApellido: String = "",
    val fechaNacimiento: String = "",
    val estadoNacimiento: String = "",
    val sexo: String = "",
    val curp: String = "",
    val nss: String = "",
    val telefono: String = "",
    val correoAlternativo: String = ""
)
// Nota: Al ser data class, provee automáticamente equals/hashCode/toString y copy(),
// lo cual se aprovecha para actualizaciones inmutables campo a campo en el ViewModel.

/**
 * ViewModel responsable de administrar y exponer el estado del perfil del usuario.
 *
 * Patrón: MVVM. La UI observa el StateFlow inmutable; el ViewModel mantiene el MutableStateFlow interno.
 * Seguridad: la UI no puede mutar el estado; todas las actualizaciones pasan por funciones explícitas.
 */
class PerfilViewModel : ViewModel() {

    /** Estado interno mutable del perfil, inicializado con valores por defecto para evitar nulls en la UI. */
    private val _usuario = MutableStateFlow(UsuarioPerfil())
    // MutableStateFlow: emite el valor actual y los subsiguientes; ideal para estado de pantalla.

    /** Estado expuesto a la UI como flujo inmutable; se recomienda collectAsState() en Compose. */
    val usuario: StateFlow<UsuarioPerfil> = _usuario
    // La UI se suscribe a este flujo y se re-renderiza automáticamente cuando cambian los valores.

    /**
     * Carga/establece el perfil inicial asignando el nombre de usuario recibido.
     *
     * @param username Identificador del usuario que se reflejará en el estado.
     *
     * Detalle: se usa viewModelScope.launch incluso para asignaciones simples,
     * dejando preparado el punto de entrada para futuras cargas asíncronas (API/DB).
     */
    fun cargarPerfil(username: String) {
        viewModelScope.launch {
            // Emite un nuevo objeto de estado con el campo 'usuario' actualizado.
            // Al crear una nueva instancia, mantenemos la inmutabilidad del estado.
            _usuario.value = UsuarioPerfil(usuario = username)
        }
    }

    /** Actualiza el campo `usuario` del perfil de forma inmutable mediante copy(). */
    fun actualizarUsuario(valor: String) { _usuario.value = _usuario.value.copy(usuario = valor) }

    /** Actualiza el campo `correoInstitucional` del perfil de forma inmutable mediante copy(). */
    fun actualizarCorreoInstitucional(valor: String) { _usuario.value = _usuario.value.copy(correoInstitucional = valor) }

    /** Actualiza el campo `contrasena` del perfil de forma inmutable mediante copy(). */
    fun actualizarContrasena(valor: String) { _usuario.value = _usuario.value.copy(contrasena = valor) }

    /** Actualiza el campo `nombre` del perfil de forma inmutable mediante copy(). */
    fun actualizarNombre(valor: String) { _usuario.value = _usuario.value.copy(nombre = valor) }

    /** Actualiza el campo `primerApellido` del perfil de forma inmutable mediante copy(). */
    fun actualizarPrimerApellido(valor: String) { _usuario.value = _usuario.value.copy(primerApellido = valor) }

    /** Actualiza el campo `segundoApellido` del perfil de forma inmutable mediante copy(). */
    fun actualizarSegundoApellido(valor: String) { _usuario.value = _usuario.value.copy(segundoApellido = valor) }

    /** Actualiza el campo `fechaNacimiento` del perfil de forma inmutable mediante copy(). */
    fun actualizarFechaNacimiento(valor: String) { _usuario.value = _usuario.value.copy(fechaNacimiento = valor) }

    /** Actualiza el campo `estadoNacimiento` del perfil de forma inmutable mediante copy(). */
    fun actualizarEstadoNacimiento(valor: String) { _usuario.value = _usuario.value.copy(estadoNacimiento = valor) }

    /** Actualiza el campo `sexo` del perfil de forma inmutable mediante copy(). */
    fun actualizarSexo(valor: String) { _usuario.value = _usuario.value.copy(sexo = valor) }

    /** Actualiza el campo `curp` del perfil de forma inmutable mediante copy(). */
    fun actualizarCurp(valor: String) { _usuario.value = _usuario.value.copy(curp = valor) }

    /** Actualiza el campo `nss` del perfil de forma inmutable mediante copy(). */
    fun actualizarNss(valor: String) { _usuario.value = _usuario.value.copy(nss = valor) }

    /** Actualiza el campo `telefono` del perfil de forma inmutable mediante copy(). */
    fun actualizarTelefono(valor: String) { _usuario.value = _usuario.value.copy(telefono = valor) }

    /** Actualiza el campo `correoAlternativo` del perfil de forma inmutable mediante copy(). */
    fun actualizarCorreoAlternativo(valor: String) { _usuario.value = _usuario.value.copy(correoAlternativo = valor) }
}

/**
 * Relación con el proyecto:
 *
 * - **Presentación (UI/Compose):** Este ViewModel expone `usuario: StateFlow<UsuarioPerfil>` que las pantallas de perfil
 *   consumen con `collectAsState()`. Cambios en el flujo disparan recomposición, manteniendo la UI sincronizada.
 * - **Dominio/Datos (futuro):** Actualmente no integra un Repository, pero `cargarPerfil()` y los setters
 *   son puntos de extensión naturales para conectar con casos de uso (domain) y persistencia (data).
 * - **Navegación:** Se instancia en el grafo de navegación del flujo de perfil. Al editar campos,
 *   la UI puede validar y navegar a confirmaciones o guardar cambios según el estado expuesto.
 * - **Arquitectura:** Implementa MVVM con estado inmutable expuesto y mutabilidad controlada interna.
 *   Facilita pruebas unitarias verificando transformaciones de `_usuario.value` ante inputs de la UI.
 */