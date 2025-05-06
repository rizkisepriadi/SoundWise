package com.app.sound_wise.utils

import com.app.sound_wise.data.models.Fact
import com.app.sound_wise.data.models.Rule

class InferenceEngine(private val rules: List<Rule>, private val userInputs: Map<Fact, Double>) {
    private val inferredFacts: MutableMap<Fact, Double> = mutableMapOf()

    fun infer(target: String): Pair<String, Double>? {
        return backwardChain(target)
    }

    private fun backwardChain(goal: String): Pair<String, Double>? {
        val applicableRules = rules.filter { it.result.variable == goal }

        val results = mutableListOf<Pair<String, Double>>()

        for (rule in applicableRules) {
            var allPremisesMatched = true
            val premiseCFs = mutableListOf<Double>()

            for (premise in rule.premises) {
                // Jika sudah ada input langsung dari user
                val userCF = userInputs[premise]
                if (userCF != null) {
                    premiseCFs.add(userCF)
                    continue
                }

                // Lakukan inferensi ke belakang
                val subConclusion = backwardChain(premise.variable)
                if (subConclusion != null && subConclusion.first == premise.value) {
                    premiseCFs.add(subConclusion.second)
                } else {
                    allPremisesMatched = false
                    break
                }
            }

            if (allPremisesMatched) {
                val cfEvidence = premiseCFs.minOrNull() ?: 1.0
                val finalCF = cfEvidence * rule.cf
                val fact = rule.result
                inferredFacts[fact] = finalCF
                results.add(Pair(fact.value, finalCF))
            }
        }

        return results.maxByOrNull { it.second }
    }

    fun getAllInferred(): Map<Fact, Double> = inferredFacts
}