package com.fernandomarquezrodriguez.trabajo1trimestre.model.server.results.recipeResult

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
): Parcelable