package com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class RecipeRemoteResult(
    val number: Int,
    val offset: Int,
    val results: List<Recipe>,
    val totalResults: Int
) : Parcelable

@Parcelize
data class Recipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
) : Parcelable