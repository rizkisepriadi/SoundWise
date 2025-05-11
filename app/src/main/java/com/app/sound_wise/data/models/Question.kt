package com.app.sound_wise.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question (
    val id: String,
    val text: String,
    val options: List<String>
): Parcelable