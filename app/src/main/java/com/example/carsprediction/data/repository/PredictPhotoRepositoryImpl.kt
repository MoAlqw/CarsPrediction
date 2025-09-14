package com.example.carsprediction.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.carsprediction.data.api.PhotoPredictApi
import com.example.carsprediction.data.model.toDomain
import com.example.carsprediction.domain.model.Prediction
import com.example.carsprediction.domain.repository.PredictPhotoRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PredictPhotoRepositoryImpl @Inject constructor(
    private val api: PhotoPredictApi
): PredictPhotoRepository {
    override suspend fun uploadPhoto(photo: Bitmap): Prediction {
        try {
            val file = bitmapToRequestBody(photo)
            val response = api.uploadPhoto(file)
            Log.d("MyTag", "${response.body()}")
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

    fun bitmapToRequestBody(bitmap: Bitmap): MultipartBody.Part {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos)
        val bitmapData = bos.toByteArray()

        val requestBody = bitmapData.toRequestBody("image/jpeg".toMediaType())

        return MultipartBody.Part.createFormData("file", "photo.jpg", requestBody)
    }
}