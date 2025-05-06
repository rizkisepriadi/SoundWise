package com.app.sound_wise.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fact(
    val variable: String,
    val value: String
) : Parcelable
