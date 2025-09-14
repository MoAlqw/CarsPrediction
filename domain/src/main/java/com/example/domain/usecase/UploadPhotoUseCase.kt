package com.example.domain.usecase

import com.example.domain.model.Photo
import com.example.domain.model.Prediction
import com.example.domain.repository.PredictPhotoRepository

class UploadPhotoUseCase(
    private val repository: PredictPhotoRepository
) {
    suspend operator fun invoke(photo: Photo): Prediction {
        return repository.uploadPhoto(photo)
    }
}