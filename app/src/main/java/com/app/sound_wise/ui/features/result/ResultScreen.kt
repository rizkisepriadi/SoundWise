package com.app.sound_wise.ui.features.result

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.sound_wise.ui.features.common.CustomButton
import com.app.sound_wise.ui.features.question.QuestionsViewModel


@Composable
fun ResultScreen(
    facts: List<Pair<String, String>>, viewModel: QuestionsViewModel, onClick: () -> Unit
) {
    val colorSheme = MaterialTheme.colorScheme
    val typographySheme = MaterialTheme.typography
    var inferenceResult by remember { mutableStateOf<Pair<String, Double>?>(null) }

    LaunchedEffect(viewModel.answers) {
        if (viewModel.answers.size == viewModel.questions.size) {
            inferenceResult = viewModel.runInference("Rekomendasi")
        } else {
            Log.w("ResultScreen", "Belum semua pertanyaan dijawab!")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(44.dp))

        Text(
            "Hasil Rekomendasi", style = typographySheme.headlineLarge, fontWeight = FontWeight.Bold
        )
        inferenceResult?.let { (value, cf) ->
            Text("${(cf * 100).toInt()}%", fontSize = 96.sp, fontWeight = FontWeight.Bold)
            Text(value, style = typographySheme.headlineSmall, fontWeight = FontWeight.Bold)
        } ?: Text("Hasil Tidak Ditemukan", style = typographySheme.headlineLarge)

        Spacer(Modifier.height(34.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = colorSheme.primaryContainer
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Detail",
                    style = typographySheme.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                facts.forEach { (key, value) ->
                    Text("$key : $value", style = typographySheme.bodyLarge)
                }
                Spacer(Modifier.height(12.dp))
            }
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom, ) {
            CustomButton(
                text = "Selesai",
                height = 50,
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick
            )
        }
    }
}
