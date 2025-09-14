package com.example.carsprediction.presentation.model

import com.example.carsprediction.domain.model.CleanlinessType
import com.example.carsprediction.domain.model.DamageType
import com.example.carsprediction.domain.model.Prediction

sealed class PredictionState {
    object Loading : PredictionState()
    data class Success(val prediction: Prediction) : PredictionState()
    data class Error(val e: String): PredictionState()
}