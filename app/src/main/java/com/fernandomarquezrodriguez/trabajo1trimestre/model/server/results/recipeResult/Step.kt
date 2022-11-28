package com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val number: Int,
    val step: String
):Parcelable