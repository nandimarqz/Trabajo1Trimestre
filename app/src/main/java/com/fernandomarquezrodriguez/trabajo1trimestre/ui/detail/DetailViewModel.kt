package com.fernandomarquezrodriguez.trabajo1trimestre.ui.detail

import androidx.lifecycle.*
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.RemoteConnection
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.Recipe
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult.AnalyzedInstruction
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult.ExtendedIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(recipe: Recipe, idRecipe: String, apiKey: String): ViewModel() {

    private val _state = MutableLiveData(IngredientsUiState())
    val state : LiveData<IngredientsUiState> get() = _state

    init {

        //La ejecucion se va realizar por el hilo principal
        viewModelScope.launch(Dispatchers.Main) {

            //se iguala la recetea a la recibida por parametro
            _state.value = _state.value?.copy(recipe = recipe)
            //Coge el resultado de la llamada a la api
            val result = RemoteConnection.ingredientsService.ingredients(idRecipe, apiKey)
            //Mapea el resultado y coge los ingredientes
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
            //Mapea del resultado las instrucciones y las guarda en una variable
            val steps = result.analyzedInstructions.map {

                AnalyzedInstruction(
                    it.name,
                    it.steps,
                )

            }
            //Iguala los ingredientes y los pasos a los mapeados
            _state.value = _state.value?.copy(ingredients = ingredients , steps = steps )
        }

    }

}

//Clase que contiene la variables que se van a usar
data class  IngredientsUiState(

    val recipe: Recipe? = null,
    val ingredients: List<ExtendedIngredient>? = null,
    val steps : List<AnalyzedInstruction>? = null

)

//Factoria del detailViewModel
@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val recipe: Recipe, private val idRecipe: String,  private val apiKey: String  ): ViewModelProvider.Factory{

    override fun <T: ViewModel> create(modelClass: Class<T>):T{

        return DetailViewModel(recipe, idRecipe, apiKey) as T

    }

}