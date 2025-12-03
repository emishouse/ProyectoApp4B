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
        _user.value = value
    }

    fun onPassChange(value: String) {
        _pass.value = value
    }

    fun login() {
        if (user.value == "UTM241018TI" && pass.value == "EduardoTI18") {
            _loginState.value = LoginState.Success
        } else if (user.value == "Chucho" && pass.value == "123"){
            _loginState.value = LoginState.Success
        }else if (user.value == "Humberto" && pass.value == "123"){
            _loginState.value = LoginState.Success
        }else if (user.value == "Mahal" && pass.value == "123"){
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error
        }
    }
}
