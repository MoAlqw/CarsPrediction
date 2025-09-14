package com.example.data.repository

import com.example.data.api.PhotoPredictApi
import com.example.data.model.toDomain
import com.example.domain.model.Photo
import com.example.domain.model.Prediction
import com.example.domain.repository.PredictPhotoRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PredictPhotoRepositoryImpl @Inject constructor(
    private val api: PhotoPredictApi
): PredictPhotoRepository {
    override suspend fun uploadPhoto(photo: Photo): Prediction {
        try {
            val file = bitmapToRequestBody(photo)
            val response = api.uploadPhoto(file)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    return result.toDomain()
                } else {
                    throw Exception("Server returned empty body")
                }
            } else {
                throw Exception("Server error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error uploading photo: ${e.message}", e)
        }
    }

    fun bitmapToRequestBody(photo: Photo): MultipartBody.Part {
        val requestBody = photo.bytes.toRequestBody("image/jpeg".toMediaType())
        return MultipartBody.Part.createFormData("file", "photo.jpg", requestBody)
    }
}