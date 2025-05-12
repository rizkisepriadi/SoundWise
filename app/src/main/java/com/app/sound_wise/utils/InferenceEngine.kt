package com.app.sound_wise.utils

import com.app.sound_wise.data.models.Fact
import com.app.sound_wise.data.models.Rule

class InferenceEngine(private val rules: List<Rule>, private val userInputs: Map<Fact, Double>) {
    private val inferredFacts: MutableMap<Fact, Double> = mutableMapOf()

    fun infer(goalVariable: String): Pair<String, Double>? {
        val possibleGoals = rules.map { it.result }
            .filter { it.variable == goalVariable }
            .distinct()

        val results = possibleGoals.mapNotNull { fact ->
            val cf = backwardChain(fact)
            if (cf != null) fact.value to cf else null
        }

        return results.maxByOrNull { it.second }
    }

    private fun backwardChain(goal: Fact): Double? {
        userInputs[goal]?.let {
            println("User input for ${goal.variable}: ${goal.value} -> ${it}")
            return it
        }

        inferredFacts[goal]?.let {
            println("Inferred fact for ${goal.variable}: ${goal.value} -> ${it}")
            return it
        }

        val applicableRules = rules.filter { it.result == goal }
        println("Applicable rules for goal ${goal.variable}: $applicableRules")

        val cfResults = mutableListOf<Double>()

        for (rule in applicableRules) {
            val premiseCFs = mutableListOf<Double>()
            var allPremisesMatched = true

            for (premise in rule.premises) {
                println("Checking premise: ${premise.variable} = ${premise.value}")

                val userCF = userInputs[premise]
                println("User input for premise ${premise.variable}: ${userCF}")

                if (userCF != null) {
                    if (premise.value == goal.value) {
                        premiseCFs.add(userCF)
                        continue
                    }
                }

                val cf = backwardChain(premise)
                println("Sub-conclusion for premise ${premise.variable}: ${cf}")
                if (cf != null) {
                    premiseCFs.add(cf)
                } else {
                    allPremisesMatched = false
                    break
                }
            }

            if (allPremisesMatched) {
                val cfEvidence = premiseCFs.minOrNull() ?: 1.0
                val finalCF = cfEvidence * rule.cf
                cfResults.add(finalCF)
                inferredFacts[goal] = finalCF
                println("Inferred fact: ${goal.variable} = ${goal.value}, CF = $finalCF")
            }
        }

        return cfResults.maxOrNull()
    }
}