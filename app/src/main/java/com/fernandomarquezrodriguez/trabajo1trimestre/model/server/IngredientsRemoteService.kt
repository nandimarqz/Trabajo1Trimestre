package com.fernandomarquezrodriguez.trabajo1trimestre.model.server

import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult.IngredientsRemoteResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IngredientsRemoteService {

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun ingredients(@Path("id")idIngredient:String,
                             @Query("apiKey")apiKey:String): IngredientsRemoteResult

}