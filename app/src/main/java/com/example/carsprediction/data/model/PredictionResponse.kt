package com.example.carsprediction.data.model

import com.example.carsprediction.domain.model.CleanlinessType
import com.example.carsprediction.domain.model.DamageType
import com.example.carsprediction.domain.model.Prediction

data class PredictionResponse(
    val damage: Int,
    val cleanliness: Int
)

fun PredictionResponse.toDomain(): Prediction {
    val damageType = when (damage) {
        0 -> DamageType.Clear
        1 -> DamageType.Corrosion
        2 -> DamageType.Dent
        3 -> DamageType.Scratch
        else -> DamageType.Clear
    }

    val cleanlinessType = when (cleanliness) {
        0 -> CleanlinessType.Clean
        1 -> CleanlinessType.Dirty
        else -> CleanlinessType.Clean
    }

    return Prediction(damageType, cleanlinessType)
}
