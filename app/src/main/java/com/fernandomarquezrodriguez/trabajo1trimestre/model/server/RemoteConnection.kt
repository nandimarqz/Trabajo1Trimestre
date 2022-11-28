package com.fernandomarquezrodriguez.trabajo1trimestre.model.server

import androidx.viewbinding.BuildConfig
import com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult.IngredientsRemoteResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RemoteConnection {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {

        level= if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val builder = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RecipeRemoteService = builder.create()
    val ingredientsService: IngredientsRemoteService = builder.create()
}