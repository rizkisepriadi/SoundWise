package com.app.sound_wise.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInputState(
    val fact: Fact,
    val cf: Double
) : Parcelable
