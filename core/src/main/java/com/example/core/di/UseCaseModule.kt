package com.example.core.di

import com.example.domain.repository.PredictPhotoRepository
import com.example.domain.usecase.UploadPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUploadPhotoUseCase(
        repository: PredictPhotoRepository
    ): UploadPhotoUseCase = UploadPhotoUseCase(repository)
}