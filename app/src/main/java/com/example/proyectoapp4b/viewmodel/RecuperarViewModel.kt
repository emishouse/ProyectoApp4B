package com.example.proyectoapp4b.viewmodel
// Paquete que contiene los ViewModels de la aplicación.
// "viewmodel" indica que pertenece a la capa de presentación (MVVM).

import androidx.lifecycle.ViewModel
// Clase base de Android para ViewModels.
// Permite manejar lógica y estado desacoplado de la UI, sobreviviendo a cambios de configuración.

import androidx.lifecycle.viewModelScope
// Proporciona un CoroutineScope ligado al ciclo de vida del ViewModel.
// Todas las corrutinas lanzadas aquí se cancelan automáticamente cuando el ViewModel se destruye.

import com.example.proyectoapp4b.data.repository.SigoRepository
// Repositorio que encapsula la lógica de acceso a datos.
// En este caso, expone la función recuperarContrasena() para enviar correos de recuperación.

import kotlinx.coroutines.flow.MutableStateFlow
// Flujo mutable que permite emitir y actualizar valores.
// Ideal para manejar estado reactivo en Compose.

import kotlinx.coroutines.flow.StateFlow
// Versión inmutable del flujo expuesto a la UI.
// Garantiza que la UI solo observe y no modifique directamente el estado.

import kotlinx.coroutines.launch
// Función de extensión para iniciar corrutinas dentro de un CoroutineScope.
// Se usa para ejecutar tareas asíncronas (ej. llamadas al repositorio).

/**
 * ViewModel encargado de manejar el flujo de recuperación de contraseña.
 *
 * Responsabilidad: coordinar la interacción entre la UI y el repositorio [SigoRepository]
 * para enviar correos de recuperación, exponiendo un estado observable.
 *
 * Patrón: MVVM. La UI observa el flujo `estado` y se actualiza automáticamente
 * cuando cambian los datos.
 */
class RecuperarViewModel(
    private val repo: SigoRepository // Dependencia inyectada: repositorio de datos.
) : ViewModel() {

    /** Estado interno mutable que refleja el proceso de recuperación. */
    private val _estado = MutableStateFlow<Estado>(Estado.Idle)
    /** Estado expuesto a la UI como flujo inmutable. */
    val estado: StateFlow<Estado> = _estado

    /**
     * Representa los posibles estados del proceso de recuperación.
     *
     * - Idle: estado inicial, sin interacción.
     * - Loading: proceso en curso (enviando correo).
     * - Exito: correo enviado correctamente.
     * - Error: fallo en el envío, con mensaje descriptivo.
     */
    sealed class Estado {
        object Idle : Estado()
        object Loading : Estado()
        object Exito : Estado()
        data class Error(val mensaje: String) : Estado()
    }

    /**
     * Envía un correo de recuperación de contraseña.
     *
     * @param matricula Identificador del usuario (ej. matrícula escolar).
     *
     * Flujo:
     * 1. Cambia el estado a `Loading`.
     * 2. Llama al repositorio para intentar enviar el correo.
     * 3. Actualiza el estado a `Exito` si la operación fue exitosa,
     *    o a `Error` con un mensaje si falló.
     *
     * Se ejecuta dentro de una corrutina para no bloquear la UI.
     */
    fun enviarCorreo(matricula: String) {
        viewModelScope.launch {
            _estado.value = Estado.Loading
            val ok = repo.recuperarContrasena(matricula)
            _estado.value = if (ok) Estado.Exito else Estado.Error("No se pudo enviar el correo.")
        }
    }
}

/**
 * Relación con el proyecto:
 *
 * - **Repositorio:** Depende de `SigoRepository`, que implementa la lógica de envío de correos.
 * - **UI/Compose:** La pantalla de recuperación observa `estado: StateFlow<Estado>` y
 *   reacciona mostrando feedback (loading, éxito, error).
 * - **Arquitectura:** Implementa MVVM con estado inmutable expuesto y mutabilidad controlada interna.
 * - **Navegación:** Se integra en el flujo de recuperación de credenciales; tras `Exito`,
 *   la UI puede redirigir al login o mostrar confirmación.
 * - **Extensibilidad:** Actualmente maneja un único caso de uso (recuperar contraseña),
 *   pero puede ampliarse para otros procesos relacionados con cuentas de usuario.
 */