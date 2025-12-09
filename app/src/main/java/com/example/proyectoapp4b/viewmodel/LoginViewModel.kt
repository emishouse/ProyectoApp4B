package com.example.proyectoapp4b.viewmodel
// Paquete que contiene los ViewModels de la aplicación.
// "viewmodel" indica que pertenece a la capa de presentación (MVVM).

import androidx.lifecycle.ViewModel
// Clase base de Android para ViewModels.
// Permite manejar lógica y estado desacoplado de la UI, sobreviviendo a cambios de configuración.

import kotlinx.coroutines.flow.MutableStateFlow
// Flujo mutable que permite emitir y actualizar valores.
// Ideal para manejar estado reactivo en Compose.

import kotlinx.coroutines.flow.StateFlow
// Versión inmutable del flujo expuesto a la UI.
// Garantiza que la UI solo observe y no modifique directamente el estado.

/**
 * ViewModel encargado de manejar el estado de inicio de sesión.
 *
 * Responsabilidad: almacenar usuario, contraseña y estado de login,
 * exponiendo flujos inmutables para que la UI observe cambios.
 *
 * Patrón: MVVM. La UI observa los StateFlow y se actualiza automáticamente
 * cuando cambian los datos.
 */
class LoginViewModel : ViewModel() {

    /** Estado interno mutable que contiene el nombre de usuario. */
    private val _user = MutableStateFlow("")
    /** Estado expuesto a la UI como flujo inmutable. */
    val user: StateFlow<String> = _user

    /** Estado interno mutable que contiene la contraseña. */
    private val _pass = MutableStateFlow("")
    /** Estado expuesto a la UI como flujo inmutable. */
    val pass: StateFlow<String> = _pass

    /**
     * Representa los posibles estados del proceso de login.
     *
     * - None: estado inicial o reseteado.
     * - Success: credenciales correctas.
     * - Error: credenciales incorrectas.
     */
    sealed class LoginState {
        object None : LoginState()
        object Success : LoginState()
        object Error : LoginState()
    }

    /** Estado interno mutable que contiene el resultado del login. */
    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    /** Estado expuesto a la UI como flujo inmutable. */
    val loginState: StateFlow<LoginState> = _loginState

    /**
     * Actualiza el nombre de usuario.
     *
     * @param value Texto ingresado por el usuario.
     *
     * Detalles:
     * - `trim()` elimina espacios al inicio y al final.
     * - `uppercase()` convierte todo a mayúsculas.
     * - Se resetea el estado de login a `None` para indicar que aún no se validó.
     */
    fun onUserChange(value: String) {
        _user.value = value.trim().uppercase() // 🔠 convierte a mayúsculas
        _loginState.value = LoginState.None   // 🔄 resetea estado al escribir
    }

    /**
     * Actualiza la contraseña ingresada.
     *
     * @param value Texto ingresado por el usuario.
     *
     * Detalles:
     * - `trim()` elimina espacios al inicio y al final.
     * - Se resetea el estado de login a `None` para indicar que aún no se validó.
     *
     * Ejemplo de métodos relacionados:
     * - string.trim() → elimina espacios en blanco al inicio y al final.
     * - string.trimStart() → elimina solo al inicio.
     * - string.trimEnd() → elimina solo al final.
     */
    fun onPassChange(value: String) {
        _pass.value = value.trim()
        _loginState.value = LoginState.None   // 🔄 resetea estado al escribir
    }

    /**
     * Valida las credenciales ingresadas.
     *
     * Si las combinaciones de usuario y contraseña coinciden con las predefinidas,
     * se actualiza el estado a `Success`. En caso contrario, se marca como `Error`.
     *
     * Nota: actualmente las credenciales están hardcodeadas para pruebas.
     * En producción deberían validarse contra un repositorio o servicio seguro.
     */
    fun login() {
        if (user.value == "UTM241018TI" && pass.value == "EduardoTI18") {
            _loginState.value = LoginState.Success
        } else if (user.value == "UTM241017TI" && pass.value == "12345") {
            _loginState.value = LoginState.Success
        } else if (user.value == "UTM241016TI" && pass.value == "1234") {
            _loginState.value = LoginState.Success
        } else if (user.value == "UTM241015TI" && pass.value == "123") {
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error
        }
    }

    /**
     * Resetea el estado de login y limpia los campos de usuario y contraseña.
     *
     * Uso: se invoca al cerrar sesión o al reiniciar el formulario.
     */
    fun resetLogin() {
        _user.value = ""
        _pass.value = ""
        _loginState.value = LoginState.None
    }
}

/**
 * Relación con el proyecto:
 *
 * - **UI/Compose:** Este ViewModel se conecta con la pantalla de login,
 *   proporcionando estado reactivo para usuario, contraseña y resultado.
 * - **Arquitectura:** Implementa MVVM, encapsulando la lógica de validación
 *   y exponiendo flujos inmutables para la UI.
 * - **Navegación:** Tras un `Success`, la UI puede navegar al flujo principal
 *   de la aplicación. En caso de `Error`, muestra mensajes de validación.
 * - **Extensibilidad:** Actualmente usa credenciales hardcodeadas, pero está
 *   preparado para integrarse con un repositorio o servicio de autenticación real.
 */