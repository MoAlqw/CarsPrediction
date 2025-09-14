package com.example.carsprediction.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsprediction.domain.usecase.UploadPhotoUseCase
import com.example.carsprediction.presentation.model.PredictionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadPhotoViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase
): ViewModel() {

    private val _state = MutableStateFlow<PredictionState>(PredictionState.Loading)
    val state: StateFlow<PredictionState> get() = _state

    fun startPredict(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = PredictionState.Loading
            try {
                val prediction = uploadPhotoUseCase(bitmap)
                _state.value = PredictionState.Success(prediction)
            } catch (e: Exception) {
                _state.value = PredictionState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}