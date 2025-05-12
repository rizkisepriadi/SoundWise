package com.app.sound_wise.ui.features.question

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.app.sound_wise.data.models.Answer
import com.app.sound_wise.data.models.Fact
import com.app.sound_wise.data.models.Question
import com.app.sound_wise.data.repositories.RuleRepository
import com.app.sound_wise.ui.base.BaseViewModel
import com.app.sound_wise.utils.InferenceEngine
import com.app.sound_wise.utils.certaintyLabelToValue

class QuestionsViewModel(
    private val ruleRepository: RuleRepository
) : BaseViewModel() {

    private val rules = ruleRepository.loadRules()


    val questions = listOf(
        Question("Ruangan", "Bagaimana kondisi ruangan?", listOf("Bergema", "Tidak Bergema")),
        Question("Suara", "Bagaimana kualitas suara?", listOf("Tidak Jernih", "Jernih")),
        Question("Microphone", "Bagaimana kondisi microphone?", listOf("Buruk", "Baik")),
        Question("Speaker", "Bagaimana kondisi speaker?", listOf("Tidak Baik", "Baik")),
        Question("Arus Listrik", "Bagaimana kondisi arus listrik?", listOf("Tidak Stabil", "Stabil")),
        Question("Amplifier", "Bagaimana kondisi amplifier?", listOf("Tidak Baik", "Baik")),
        Question("Gain", "Bagaimana pengaturan gain?", listOf("Tidak Optimal", "Optimal")),
        Question("Feedback", "Apakah terjadi feedback?", listOf("Tidak Terjadi", "Terjadi")),
        Question("Equalizer", "Bagaimana pengaturan equalizer?", listOf("Buruk", "Baik")),
        Question("Pengecekan Kabel", "Bagaimana pengecekan kabel?", listOf("Tidak Teratur", "Teratur")),
        Question("Overheat", "Apakah terjadi overheat pada alat?", listOf("Ya", "Tidak")),
        Question(
            "Perawatan Sound System",
            "Bagaimana perawatan sound system?",
            listOf("Tidak Terawat", "Terawat")
        )
    )

    private val _answers = mutableStateListOf<Answer>()
    val answers: List<Answer> get() = _answers

    fun updateAnswer(questionId: String, selected: String, certaintyLabel: String) {
        val cf = certaintyLabelToValue(certaintyLabel)
        val existing = _answers.indexOfFirst { it.questionId == questionId }
        val answer = Answer(questionId, selected, cf)
        if (existing >= 0) _answers[existing] = answer else _answers.add(answer)
    }

    fun getAnswer(questionId: String): Answer? = _answers.find { it.questionId == questionId }

    fun reset() {
        _answers.clear()
    }

    fun runInference(target: String): Pair<String, Double>? {
        val inputMap: Map<Fact, Double> = _answers.associate {
            Fact(it.questionId, it.selectedAnswer) to it.certaintyValue
        }

        Log.d("QuizViewModel", "Input to inference: $inputMap")

        val engine = InferenceEngine(rules, inputMap)
        val result = engine.infer(target)

        Log.d("QuizViewModel", "Inference output: $result")
        return result
    }
}
