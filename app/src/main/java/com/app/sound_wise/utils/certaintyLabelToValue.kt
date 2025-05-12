package com.app.sound_wise.utils

fun certaintyLabelToValue(label: String): Double = when (label) {
    "Tidak Yakin" -> 0.2
    "Kurang Yakin" -> 0.5
    "Yakin" -> 0.8
    "Sangat Yakin" -> 1.0
    else -> 0.0
}