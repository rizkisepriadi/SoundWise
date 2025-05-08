package com.app.sound_wise.ui.features.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    width: Int = 310,
    height: Int = 48,
    onClick: () -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
            .width(width.dp)
            .height(height.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}
