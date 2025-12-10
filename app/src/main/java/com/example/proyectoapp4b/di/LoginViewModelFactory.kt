package com.example.proyectoapp4b.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoapp4b.data.AuthRepository
import com.example.proyectoapp4b.viewmodel.LoginViewModel
import kotlin.jvm.java

/**
 * Fábrica (Factory) para crear instancias de [LoginViewModel].
 *
 * Esta clase permite la inyección de dependencias en el ViewModel, en este caso,
 * proporcionando el [AuthRepository] que el [LoginViewModel] necesita para funcionar.
 *
 * @param repository El repositorio de autenticación que se inyectará en el ViewModel.
 */
class LoginViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    /**
     * Crea una nueva instancia del ViewModel solicitado.
     *
     * @param modelClass La clase del ViewModel que se va a crear.
     * @return Una instancia del ViewModel con sus dependencias.
     * @throws IllegalArgumentException si la clase del ViewModel es desconocida.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw kotlin.IllegalArgumentException("Unknown ViewModel class")
    }
}
