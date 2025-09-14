package com.example.carsprediction.domain.repository

import android.graphics.Bitmap
import com.example.carsprediction.domain.model.Prediction

interface PredictPhotoRepository {
    suspend fun uploadPhoto(photo: Bitmap): Prediction
}