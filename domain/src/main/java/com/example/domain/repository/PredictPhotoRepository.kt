package com.example.domain.repository

import com.example.domain.model.Photo
import com.example.domain.model.Prediction

interface PredictPhotoRepository {
    suspend fun uploadPhoto(photo: Photo): Prediction
}