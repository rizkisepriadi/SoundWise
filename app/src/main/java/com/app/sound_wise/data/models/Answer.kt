package com.app.sound_wise.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val questionId: String,
    val selectedAnswer: String,
    val certaintyValue: Double
): Parcelable