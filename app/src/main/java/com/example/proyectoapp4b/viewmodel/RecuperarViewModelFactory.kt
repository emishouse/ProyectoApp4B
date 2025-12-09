package com.example.proyectoapp4b.viewmodel
// Paquete que contiene los ViewModels y sus factories.
// "viewmodel" indica que pertenece a la capa de presentación (MVVM).

import androidx.lifecycle.ViewModel
// Clase base de Android para ViewModels.

import androidx.lifecycle.ViewModelProvider
// Proveedor de instancias de ViewModel.
// Permite crear ViewModels con parámetros personalizados mediante una Factory.

import com.example.proyectoapp4b.data.repository.SigoRepository
// Repositorio que encapsula la lógica de acceso a datos.
// Se inyecta en el ViewModel para manejar operaciones de recuperación.

/**
 * Factory para crear instancias de [RecuperarViewModel].
 *
 * Responsabilidad: proveer un mecanismo para instanciar el ViewModel con
 * dependencias personalizadas (en este caso, un [SigoRepository]).
 *
 * Uso: se pasa al `ViewModelProvider` cuando se necesita obtener un
 * `RecuperarViewModel` dentro de una Activity o Composable.
 */
class RecuperarViewModelFactory(
    private val repository: SigoRepository // Dependencia inyectada: repositorio de datos.
) : ViewModelProvider.Factory {

    /**
     * Crea una instancia del ViewModel solicitado.
     *
     * @param modelClass Clase del ViewModel que se quiere instanciar.
     * @return Una instancia de [RecuperarViewModel] si la clase coincide.
     *
     * Detalles:
     * - Se verifica si el `modelClass` corresponde a `RecuperarViewModel`.
     * - Si coincide, se devuelve una nueva instancia con el repositorio inyectado.
     * - Si no coincide, se lanza una excepción indicando clase desconocida.
     *
     * La anotación `@Suppress("UNCHECKED_CAST")` evita advertencias de casting genérico.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecuperarViewModel::class.java)) {
            return RecuperarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * Relación con el proyecto:
 *
 * - **Repositorio:** Inyecta `SigoRepository` en el `RecuperarViewModel`, permitiendo
 *   que el ViewModel acceda a la lógica de recuperación de contraseñas.
 * - **UI/Compose:** Se utiliza en pantallas como `RecuperarContrasenaScreen` para obtener
 *   una instancia del ViewModel lista para usarse.
 * - **Arquitectura:** Implementa el patrón Factory, recomendado por Android para crear
 *   ViewModels con parámetros personalizados.
 * - **Extensibilidad:** Permite escalar fácilmente si en el futuro se requieren más
 *   dependencias o diferentes configuraciones de ViewModel.
 */
