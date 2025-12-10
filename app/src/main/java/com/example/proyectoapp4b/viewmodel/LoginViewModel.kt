package com.example.proyectoapp4b.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapp4b.data.AuthRepository
import com.example.proyectoapp4b.data.model.LoginRequest
import com.example.proyectoapp4b.data.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Estado de la interfaz de usuario para la pantalla de inicio de sesión.
 *
 * @param username El nombre de usuario actual.
 * @param password La contraseña actual.
 * @param isLoading `true` si hay una operación de inicio de sesión en curso.
 * @param loginSuccess `true` si el inicio de sesión fue exitoso.
 * @param errorMessage Mensaje de error a mostrar, o `null` si no hay error.
 * @param user La información del usuario si el inicio de sesión fue exitoso.
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null,
    val user: UserResponse? = null
)

/**
 * ViewModel para la pantalla de inicio de sesión.
 *
 * Gestiona el estado de la UI (`LoginUiState`) y maneja la lógica de negocio
 * para la autenticación del usuario a través del [AuthRepository].
 *
 * @param repository El repositorio para manejar la lógica de autenticación.
 */
class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    // Estado mutable interno que el ViewModel puede modificar
    private val _uiState = MutableStateFlow(LoginUiState())
    /**
     * Estado inmutable de la UI que se expone a la Vista para ser observado.
     */
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(
            username = newUsername.trim().uppercase(),
            errorMessage = null
        )
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(
            password = newPassword.trim(),
            errorMessage = null
        )
    }

    fun login() {
        val usernameTrimmed = _uiState.value.username.trim().uppercase()
        val passwordTrimmed = _uiState.value.password.trim()

        if (usernameTrimmed.isBlank() || passwordTrimmed.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "El usuario y la contraseña no pueden estar vacíos."
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            loginSuccess = false
        )

        viewModelScope.launch {
            val request = LoginRequest(
                username = _uiState.value.username,
                password = _uiState.value.password
            )

            val result = repository.login(request)

            _uiState.value = result.fold(
                onSuccess = { userResponse ->
                    _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = true,
                        user = userResponse,
                        errorMessage = null
                    )
                },
                onFailure = { throwable ->
                    //mensaje de error
                    val errorMessage = if (throwable.message?.contains("401") == true) {
                        "Error al iniciar sesión: Usuario o contraseña incorrectos (código: 401)"
                    } else {
                        throwable.message ?: "Error desconocido en la conexión."
                    }

                    _uiState.value.copy(
                        isLoading = false,
                        loginSuccess = false,
                        errorMessage = errorMessage
                    )
                }
            )
        }
    }

    fun clearFields() {
        _uiState.value = LoginUiState(errorMessage = null)
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