package com.example.domain.model

data class Prediction(
    val damage: DamageType,
    val cleanliness: CleanlinessType
)

sealed class DamageType {
    object Clear : DamageType()
    object Corrosion : DamageType()
    object Dent : DamageType()
    object Scratch : DamageType()
}

sealed class CleanlinessType {
    object Clean : CleanlinessType()
    object Dirty : CleanlinessType()
}