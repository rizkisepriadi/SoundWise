package com.app.sound_wise.data.repositories

import android.content.Context
import com.app.sound_wise.data.models.Rule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RuleRepository(
    private val context: Context
) {
    fun loadRules(): List<Rule> {
        val json = context.assets.open("rules.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Rule>>() {}.type
        return Gson().fromJson(json, type)
    }
}