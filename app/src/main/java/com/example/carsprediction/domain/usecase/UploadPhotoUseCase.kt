package com.example.carsprediction.domain.usecase

import android.graphics.Bitmap
import com.example.carsprediction.domain.model.Prediction
import com.example.carsprediction.domain.repository.PredictPhotoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadPhotoUseCase @Inject constructor(
    private val repository: PredictPhotoRepository
) {
    suspend operator fun invoke(photo: Bitmap): Prediction {
        return repository.uploadPhoto(photo)
    }
}