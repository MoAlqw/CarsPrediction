package com.example.carsprediction.presentation.extension

import android.graphics.Bitmap
import com.example.domain.model.Photo
import java.io.ByteArrayOutputStream

fun Bitmap.toPhoto(quality: Int = 100): Photo {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return Photo(stream.toByteArray())
}