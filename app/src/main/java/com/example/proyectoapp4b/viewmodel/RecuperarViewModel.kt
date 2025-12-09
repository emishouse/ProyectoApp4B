package com.example.proyectoapp4b.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapp4b.data.repository.SigoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecuperarViewModel(
    private val repo: SigoRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<Estado>(Estado.Idle)
    val estado: StateFlow<Estado> = _estado

    sealed class Estado {
        object Idle : Estado()
        object Loading : Estado()
        object Exito : Estado()
        data class Error(val mensaje: String) : Estado()
    }

    fun enviarCorreo(matricula: String) {
        viewModelScope.launch {
            _estado.value = Estado.Loading
            val ok = repo.recuperarContrasena(matricula)
            _estado.value = if (ok) Estado.Exito else Estado.Error("No se pudo enviar el correo.")
        }
    }
}