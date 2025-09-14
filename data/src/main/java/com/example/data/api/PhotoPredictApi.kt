package com.example.data.api

import com.example.data.model.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PhotoPredictApi {
    @Multipart
    @POST("predict")
    suspend fun uploadPhoto(
        @Part file: MultipartBody.Part
    ): Response<PredictionResponse>
}