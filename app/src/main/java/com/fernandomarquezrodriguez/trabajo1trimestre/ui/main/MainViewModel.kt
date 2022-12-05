package com.fernandomarquezrodriguez.trabajo1trimestre.ui.main

import androidx.lifecycle.*
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.RemoteConnection
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainViewModel(apiKey: String) : ViewModel() {

    val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state

    //Cuando se inicie realizara lo siguiente
    init {

        //La ejecucion se va realizar por el hilo principal
        viewModelScope.launch(Dispatchers.Main) {
            //Se pone el progress bar a true
            _state.value = _state.value?.copy(loading = true)
            //Se obtiene el resultado de la llamada a la api
            val result = RemoteConnection.service.recipes(apiKey)
            //Se mapean las recetas de los resultados y se guarda en una variable
            val recipes = result.results.map {
                Recipe(
                    it.id,
                    it.image,
                    it.imageType,
                    it.title

                )
            }
            //Se pone el progress bar a false y se igualan las recetas
            _state.value = _state.value?.copy(loading = false, recipes = recipes)
        }
    }

    //Realiza la navegacion con una receta pasada por parametro
    fun navigateTo(recipe: Recipe) {
        _state.value = _state.value?.copy(navigateTo = recipe)
    }

    //Finaliza la navegacion
    fun onNavigateDone() {
        _state.value = _state.value?.copy(navigateTo = null)
    }
    //Clase que contiene las variables que se usan
    data class UiState(
        val loading: Boolean = false,
        val recipes: List<Recipe>? = null,
        val navigateTo: Recipe? = null

    )

    //La factoria del mainViewModel
    class MainViewModelFactory(private val apiKey: String) : ViewModelProvider.Factory {

       override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(apiKey) as T
        }

    }
}
