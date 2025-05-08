package com.app.sound_wise.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.sound_wise.data.models.Fact
import com.app.sound_wise.data.models.Rule
import com.app.sound_wise.data.models.UserInputState
import com.app.sound_wise.data.repositories.RuleRepository
import com.app.sound_wise.ui.base.BaseViewModel
import com.app.sound_wise.utils.InferenceEngine

class HomeViewModel(ruleRepository: RuleRepository) : BaseViewModel() {

    private val rules: List<Rule> = ruleRepository.loadRules()

    private val _recommendation = MutableLiveData<Pair<String?, Double>>()
    val recommendation: LiveData<Pair<String?, Double>> = _recommendation

    private var userInputs: MutableMap<Fact, Double> = mutableMapOf()

    fun inferFromUserInput(inputs: List<UserInputState>) {
        userInputs = inputs.associate { it.fact to it.cf }.toMutableMap()
        val engine = InferenceEngine(rules, userInputs)
        _recommendation.value = engine.infer("Rekomendasi")
    }
}
