package com.example.core.di

import com.example.data.repository.PredictPhotoRepositoryImpl
import com.example.domain.repository.PredictPhotoRepository
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