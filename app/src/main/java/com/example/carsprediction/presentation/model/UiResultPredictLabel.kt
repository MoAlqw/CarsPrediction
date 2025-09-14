package com.example.carsprediction.presentation.model

import com.example.carsprediction.domain.model.CleanlinessType
import com.example.carsprediction.domain.model.DamageType

data class UiResultPredictLabel(
    val text: String,
    val colorRes: Int
)

fun mapDamage(damage: DamageType): UiResultPredictLabel = when (damage) {
    is DamageType.Clear -> UiResultPredictLabel("No damage", android.R.color.holo_green_dark)
    is DamageType.Corrosion -> UiResultPredictLabel("Corrosion", android.R.color.holo_orange_dark)
    is DamageType.Dent -> UiResultPredictLabel("Dent", android.R.color.holo_red_dark)
    is DamageType.Scratch -> UiResultPredictLabel("Scratch", android.R.color.holo_blue_dark)
}

fun mapCleanliness(cleanliness: CleanlinessType): UiResultPredictLabel = when (cleanliness) {
    is CleanlinessType.Clean -> UiResultPredictLabel("Clean", android.R.color.holo_green_dark)
    is CleanlinessType.Dirty -> UiResultPredictLabel("Dirty", android.R.color.holo_red_dark)
}