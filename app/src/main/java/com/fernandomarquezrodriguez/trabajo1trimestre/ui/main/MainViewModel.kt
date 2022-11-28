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

            _state.value = _state.value?.copy(loading = true)
            val result = RemoteConnection.service.recipes(apiKey)
            val recipes = result.results.map {
                Recipe(
                    it.id,
                    it.image,
                    it.imageType,
                    it.title

                )
            }
            _state.value = _state.value?.copy(loading = false, recipes = recipes)
        }
    }

    fun navigateTo(recipe: Recipe) {
        _state.value = _state.value?.copy(navigateTo = recipe)
    }

    fun onNavigateDone() {
        _state.value = _state.value?.copy(navigateTo = null)
    }

    data class UiState(
        val loading: Boolean = false,
        val recipes: List<Recipe>? = null,
        val navigateTo: Recipe? = null

    )

    class MainViewModelFactory(private val apiKey: String) : ViewModelProvider.Factory {

       override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(apiKey) as T
        }

    }
}
