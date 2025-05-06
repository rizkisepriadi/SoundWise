package com.app.sound_wise.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rule(
    val id: Int,
    val premises: List<Fact>,
    val result: Fact,
    val cf: Double
) : Parcelable
