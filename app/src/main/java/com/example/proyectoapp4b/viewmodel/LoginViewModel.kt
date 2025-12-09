package com.example.proyectoapp4b.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _user = MutableStateFlow("")
    val user: StateFlow<String> = _user

    private val _pass = MutableStateFlow("")
    val pass: StateFlow<String> = _pass

    sealed class LoginState {
        object None : LoginState()
        object Success : LoginState()
        object Error : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    val loginState: StateFlow<LoginState> = _loginState

    fun onUserChange(value: String) {
        _user.value = value.trim().uppercase() // 🔠 convierte a mayúsculas

        _loginState.value = LoginState.None   // 🔄 resetea estado al escribir
    }
    /**
     *- string.trim() → elimina espacios en blanco al inicio y al final.
     * - string.trimStart() → elimina solo al inicio.
     * - string.trimEnd() → elimina solo al final
     */
    fun onPassChange(value: String) {
        _pass.value = value.trim()
        _loginState.value = LoginState.None   //resetea estado al escribir
    }

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

    fun resetLogin() {
        _user.value = ""
        _pass.value = ""
        _loginState.value = LoginState.None
    }
}