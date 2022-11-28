package com.fernandomarquezrodriguez.trabajo1trimestre.ui.detail

import androidx.lifecycle.*
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.RemoteConnection
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult.ExtendedIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(recipe: Recipe, idRecipe: String, apiKey: String): ViewModel() {

    private val _state = MutableLiveData(IngredientsUiState())
    val state : LiveData<IngredientsUiState> get() = _state

    init {

        //La ejecucion se va realizar por el hilo principal
        viewModelScope.launch(Dispatchers.Main) {

            _state.value = _state.value?.copy(recipe = recipe)
            val result = RemoteConnection.ingredientsService.ingredients(idRecipe, apiKey)
            val ingredients = result.extendedIngredients.map {
                ExtendedIngredient(
                    it.aisle,
                    it.amount,
                    it.consistency,
                    it.id,
                    it.image,
                    it.measures,
                    it.meta,
                    it.name,
                    it.nameClean,
                    it.original,
                    it.originalName,
                    it.unit

                )
            }
            _state.value = _state.value?.copy(ingredients = ingredients )
        }

    }

}

data class  IngredientsUiState(

    val recipe: Recipe? = null,
    val ingredients: List<ExtendedIngredient>? = null

)

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val recipe: Recipe, private val idRecipe: String,  private val apiKey: String  ): ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>):T{

        return DetailViewModel(recipe, idRecipe, apiKey) as T

    }

}