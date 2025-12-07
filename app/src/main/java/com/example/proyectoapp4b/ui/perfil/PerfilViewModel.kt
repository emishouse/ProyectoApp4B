package com.example.proyectoapp4b.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Data class con todos los campos del perfil
data class Usuario(
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

class PerfilViewModel : ViewModel() {

    // Estado del perfil
    private val _usuario = MutableStateFlow(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

    // Simulación de carga desde API
    fun cargarPerfil(username: String) {
        viewModelScope.launch {
            _usuario.value = Usuario(usuario = username)
        }
    }

    // Funciones para actualizar cada campo
    fun actualizarUsuario(valor: String) { _usuario.value = _usuario.value.copy(usuario = valor) }
    fun actualizarCorreoInstitucional(valor: String) { _usuario.value = _usuario.value.copy(correoInstitucional = valor) }
    fun actualizarContrasena(valor: String) { _usuario.value = _usuario.value.copy(contrasena = valor) }
    fun actualizarNombre(valor: String) { _usuario.value = _usuario.value.copy(nombre = valor) }
    fun actualizarPrimerApellido(valor: String) { _usuario.value = _usuario.value.copy(primerApellido = valor) }
    fun actualizarSegundoApellido(valor: String) { _usuario.value = _usuario.value.copy(segundoApellido = valor) }
    fun actualizarFechaNacimiento(valor: String) { _usuario.value = _usuario.value.copy(fechaNacimiento = valor) }
    fun actualizarEstadoNacimiento(valor: String) { _usuario.value = _usuario.value.copy(estadoNacimiento = valor) }
    fun actualizarSexo(valor: String) { _usuario.value = _usuario.value.copy(sexo = valor) }
    fun actualizarCurp(valor: String) { _usuario.value = _usuario.value.copy(curp = valor) }
    fun actualizarNss(valor: String) { _usuario.value = _usuario.value.copy(nss = valor) }
    fun actualizarTelefono(valor: String) { _usuario.value = _usuario.value.copy(telefono = valor) }
    fun actualizarCorreoAlternativo(valor: String) { _usuario.value = _usuario.value.copy(correoAlternativo = valor) }
}