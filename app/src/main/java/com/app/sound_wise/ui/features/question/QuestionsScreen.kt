package com.app.sound_wise.ui.features.question

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.sound_wise.R
import com.app.sound_wise.ui.features.common.CustomButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionsScreen(
    viewModel: QuestionsViewModel,
    index: Int,
    onNext: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    val colorSheme = MaterialTheme.colorScheme
    val typographySheme = MaterialTheme.typography
    val totalQuestions = viewModel.questions.size
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val questionData = viewModel.questions.getOrNull(index) ?: return
    val answerState = viewModel.getAnswer(questionData.id)

    val questionText = questionData.text
    val options = questionData.options
    val selectedOption = answerState?.selectedAnswer
    val selectedCertainty = when (answerState?.certaintyValue) {
        0.2 -> "Tidak Yakin"
        0.5 -> "Kurang Yakin"
        0.8 -> "Yakin"
        1.0 -> "Sangat Yakin"
        else -> null
    }

    val certaintyOptions = listOf("Tidak Yakin", "Kurang Yakin", "Yakin", "Sangat Yakin")
    val currentProgress by remember(index) { mutableStateOf((index + 1f) / totalQuestions) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorSheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.sound_wise),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(149.dp)
                    .height(33.dp)
            )
            InfoIconButton("Gunakan aplikasi Decibel Master untuk melakukan pengukuran terhadap suara")

        }

        Spacer(Modifier.height(32.dp))

        LinearProgressIndicator(
            progress = { currentProgress },
            color = colorSheme.primary,
            trackColor = colorSheme.secondary.copy(alpha = 0.3f),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = questionText,
            style = typographySheme.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = colorSheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                CustomButton(
                    text = option,
                    color = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == option) colorSheme.primary else colorSheme.secondary,
                        contentColor = colorSheme.onPrimary
                    ),
                    onClick = {
                        viewModel.updateAnswer(
                            questionId = questionData.id,
                            selected = option,
                            certaintyLabel = selectedCertainty ?: certaintyOptions.first()
                        )
                    },
                )
            }
        }

        Spacer(Modifier.height(32.dp))
        Text(
            "Seberapa yakin?",
            color = colorSheme.onBackground,
            fontWeight = FontWeight.Bold,
            style = typographySheme.headlineSmall
        )

        Spacer(Modifier.height(12.dp))


        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            certaintyOptions.forEach { certaintyLabel ->
                OutlinedButton(
                    onClick = {
                        viewModel.updateAnswer(
                            questionId = questionData.id,
                            selected = selectedOption ?: "",
                            certaintyLabel = certaintyLabel
                        )
                    },
                    border = BorderStroke(1.dp, colorSheme.outline),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedCertainty == certaintyLabel) Color.Gray else Color.White
                    )
                ) {
                    Text(
                        certaintyLabel,
                        color = if (selectedCertainty == certaintyLabel) colorSheme.onPrimary else colorSheme.onBackground
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            snackbar = { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(8.dp),
                )
            }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = CircleShape)
                        .background(color = colorSheme.primaryContainer)
                        .border(
                            border = BorderStroke(3.dp, colorSheme.onPrimaryContainer),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colorSheme.onPrimaryContainer
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
            IconButton(
                onClick = {
                    if (selectedOption.isNullOrBlank() || selectedCertainty == null) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Silakan pilih jawaban dan tingkat keyakinan terlebih dahulu.")
                        }
                    } else {
                        onNext()
                    }
                },
                modifier = Modifier
                    .size(60.dp)
                    .clip(shape = CircleShape)
                    .background(color = colorSheme.primaryContainer)
                    .border(
                        border = BorderStroke(3.dp, colorSheme.onPrimaryContainer),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = colorSheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun InfoIconButton(infoMessage: String) {
    val openDialog = remember { mutableStateOf(false) }
    val colorScheme = MaterialTheme.colorScheme

    IconButton(onClick = { openDialog.value = true }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = colorScheme.primary
        )
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("OK")
                }
            },
            title = { Text("Informasi Parameter") },
            text = { Text(infoMessage, style = MaterialTheme.typography.bodyLarge) },
        )
    }
}