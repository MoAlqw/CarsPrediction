package com.example.carsprediction.common.di

import com.example.carsprediction.data.repository.PredictPhotoRepositoryImpl
import com.example.carsprediction.domain.repository.PredictPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPredictPhotoRepository(
        impl: PredictPhotoRepositoryImpl
    ): PredictPhotoRepository
}