package com.app.sound_wise.ui.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.sound_wise.data.models.Fact
import com.app.sound_wise.data.models.UserInputState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val result by viewModel.recommendation.observeAsState()

    // Daftar pertanyaan input, hardcoded atau bisa dari remote di kemudian hari
    val questions = remember {
        listOf(
            Fact("Ruangan", "Tidak Bergema"),
            Fact("Suara", "Jernih"),
            Fact("Microphone", "Baik"),
            Fact("Speaker", "Baik"),
            Fact("Arus Listrik", "Stabil"),
            Fact("Amplifier", "Baik"),
            Fact("Gain", "Optimal"),
            Fact("Feedback", "Tidak Terjadi"),
            Fact("Equalizer", "Baik"),
            Fact("Pengecekan Kabel", "Teratur"),
            Fact("Overheat", "Tidak"),
            Fact("Perawatan Sound System", "Terawat"),
        )
    }

    var userInputs by remember {
        mutableStateOf(questions.map { UserInputState(it, 1.0) })
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Input Gejala", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(userInputs.size) { index ->
                val input = userInputs[index]
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = input.fact.variable)
                    Slider(
                        value = input.cf.toFloat(),
                        onValueChange = { newValue ->
                            userInputs = userInputs.toMutableList().also {
                                it[index] = input.copy(cf = newValue.toDouble())
                            }
                        },
                        valueRange = 0f..1f,
                        steps = 9,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = "Keyakinan: ${"%.1f".format(input.cf)}")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.inferFromUserInput(userInputs)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Diagnosa Sekarang")
        }

        result?.let { (hasil, cf) ->
            Spacer(Modifier.height(24.dp))
            Text("Hasil Rekomendasi:", style = MaterialTheme.typography.titleMedium)
            Text("• $hasil (CF: ${"%.2f".format(cf)})")
        }
    }
}
