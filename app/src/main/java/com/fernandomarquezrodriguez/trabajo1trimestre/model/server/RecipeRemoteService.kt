package com.fernandomarquezrodriguez.trabajo1trimestre.model.server

import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.RecipeRemoteResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeRemoteService {

    @GET("recipes/complexSearch?&number=200")
    suspend fun recipes(@Query("apiKey")apiKey:String): RecipeRemoteResult

}